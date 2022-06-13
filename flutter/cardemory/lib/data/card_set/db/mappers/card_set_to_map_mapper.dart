import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:injectable/injectable.dart';

@injectable
class CardSetToMapMapper {
  Map<String, dynamic> map(CardSet cardSet) {
    return {
      if (cardSet.id != CardSet.unknownId) CardSetTable.propertyId: cardSet.id,
      CardSetTable.propertyName: cardSet.name,
    };
  }
}
