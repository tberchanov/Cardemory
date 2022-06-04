import 'package:dartz/dartz.dart';

extension EitherExt<L, R> on Either<L, R> {
  L leftOrError() {
    L? left = fold((l) => l, (r) => null);
    if (left == null) {
      throw Exception("Left is null!");
    }
    return left;
  }

  R rightOrError() {
    R? right = fold((l) => null, (r) => r);
    if (right == null) {
      throw Exception("Right is null!");
    }
    return right;
  }
}
