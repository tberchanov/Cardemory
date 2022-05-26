import 'package:equatable/equatable.dart';

abstract class Failure extends Equatable {
  final List properties;

  const Failure([this.properties = const <dynamic>[]]);

  @override
  List<Object> get props => [...properties];
}

class FailureWithMessage extends Failure {
  final String message;

  const FailureWithMessage(this.message);

  @override
  String toString() {
    return "FailureWithMessage message: $message";
  }
}
