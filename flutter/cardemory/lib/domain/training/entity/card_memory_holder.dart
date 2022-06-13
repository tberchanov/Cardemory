import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/memory/entity/memory_holder.dart';

class CardMemoryHolder extends MemoryHolder {
  Card card;

  @override
  int get lastTrainingMillis => card.lastTrainingMillis;

  @override
  set lastTrainingMillis(int val) => card = card.copyWith(lastTrainingMillis: val);

  @override
  double get memoryRank => card.memoryRank;

  @override
  set memoryRank(double val) => card = card.copyWith(memoryRank: val);

  CardMemoryHolder(this.card);
}
