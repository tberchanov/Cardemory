import 'package:cardemory/data/card/db/card_table.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:injectable/injectable.dart';

@injectable
class MapToCardMapper {
  Card map(Map<String, dynamic> map) {
    return Card(
      map[CardTable.propertyCardSetId],
      map[CardTable.propertyTitle],
      map[CardTable.propertyDescription],
      map[CardTable.propertyMemoryRank],
      map[CardTable.propertyLastTrainingMillis],
      id: map[CardTable.propertyId],
    );
  }
}
