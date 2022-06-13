import 'dart:math';

import 'package:cardemory/domain/memory/entity/memory_holder.dart';

class MemoryManager {
  static const double maxMemoryRank = 1.0;
  static const double minMemoryRank = 0.0;
  static const double millisInDay = 1000 * 60 * 60 * 24;
  static const double forgettingScaleFactor = 30.0;
  static const double oxSigmoidTransition = 6.0;
  static const double sigmoidScaleFactor = 0.5;

  double forget(MemoryHolder memoryHolder) {
    final currentTimeMillis = DateTime.now().millisecondsSinceEpoch;
    final lastTrainingMillisDelta =
        memoryHolder.lastTrainingMillis <= 0 ? 0 : currentTimeMillis - memoryHolder.lastTrainingMillis;
    final lastTrainingDaysDelta = _millisToDay(lastTrainingMillisDelta);
    double calculatedMemoryRank = 1.0 - exp(-lastTrainingDaysDelta / (forgettingScaleFactor / memoryHolder.memoryRank));
    if (calculatedMemoryRank >= memoryHolder.memoryRank) {
      calculatedMemoryRank = minMemoryRank;
    }

    memoryHolder.memoryRank = calculatedMemoryRank;
    memoryHolder.lastTrainingMillis = currentTimeMillis;
    return calculatedMemoryRank;
  }

  double remember(MemoryHolder memoryHolder) {
    final currentTimeMillis = DateTime.now().millisecondsSinceEpoch;
    final lastTrainingMillisDelta =
        memoryHolder.lastTrainingMillis <= 0 ? 0 : currentTimeMillis - memoryHolder.lastTrainingMillis;
    final lastTrainingDaysDelta = _millisToDay(lastTrainingMillisDelta);
    double calculatedMemoryRank = 1.0 / (1.0 + exp(-lastTrainingDaysDelta * sigmoidScaleFactor + oxSigmoidTransition));
    if (calculatedMemoryRank > maxMemoryRank) {
      calculatedMemoryRank = maxMemoryRank;
    }

    memoryHolder.memoryRank = calculatedMemoryRank;
    memoryHolder.lastTrainingMillis = currentTimeMillis;
    return calculatedMemoryRank;
  }

  double _millisToDay(int millis) => millis / millisInDay;
}
