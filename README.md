# Cardemory
Application for learning information by cards methodology.

## Mathematical model
The core functionality of Cardemory app is trainings where user should remember cards.
For intelligent algorithm that supplies cards to the user was created special mathematical model.
The model is based on the following psychological experiments:
1. https://en.wikipedia.org/wiki/Leitner_system
2. https://en.wikipedia.org/wiki/Forgetting_curve
3. https://en.wikipedia.org/wiki/Learning_curve
4. https://en.wikipedia.org/wiki/Serial-position_effect
5. https://en.wikipedia.org/wiki/Spaced_repetition

Mathematical model provides probability that the card will be taken to the training session.
This probability calculated based on card **memory rank** and the last time when card was in the training session.

## Used technologies:
1. Kotlin
2. Coroutines
3. MVP arcitecture
4. Multimodule approach
5. Dagger
6. Room
7. Timber
8. Cicerone navigation
9. Circle CI
10. Firebase Test Lab
11. Firebase Crashlytics
12. Firebase ML Kit
13. Tesseract ocr

## Future Plans
1. Fix multimodule architecture. In current approach there are a lot of **api** transitive dependencies, that is incorrect.
2. Migrate architecture from MVP to MVVM.
