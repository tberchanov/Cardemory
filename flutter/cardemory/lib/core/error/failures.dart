import 'package:equatable/equatable.dart';

abstract class Failure extends Equatable {
  List properties;

  Failure([this.properties = const <dynamic>[]]);

  @override
  List<Object> get props => [...properties];
}

class FailureWithMessage extends Failure {
  final String message;

  FailureWithMessage(this.message);

  @override
  String toString() {
    return "FailureWithMessage message: $message";
  }
}
