import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:injectable/injectable.dart';

@injectable
class MapToCardSetMapper {
  CardSet map(Map<String, dynamic> map) {
    return CardSet(
      id: map[CardSetTable.propertyId],
      name: map[CardSetTable.propertyName],
    );
  }
}
