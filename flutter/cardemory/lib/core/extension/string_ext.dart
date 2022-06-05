extension StringExt on String {
  String? substringAfter(Pattern pattern) {
    final index = indexOf(pattern);
    if (index != -1) {
      return substring(index + 1);
    }
    return null;
  }

  int? tryInt() {
    return int.tryParse(this);
  }
}
