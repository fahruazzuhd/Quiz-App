package com.example.quizapp.data.generator

import com.example.quizapp.data.model.Difficulty
import com.example.quizapp.data.model.Question

class QuestionGenerator {

    fun generateQuestions(difficulty: Difficulty, count: Int = 10): List<Question> {
        val rawQuestions = when (difficulty) {
            Difficulty.EASY -> easyQuestions
            Difficulty.MEDIUM -> mediumQuestions
            Difficulty.HARD -> hardQuestions
        }

        return rawQuestions
            .shuffled()
            .take(count)
            .mapIndexed { index, question ->
                val shuffledOptions = question.options.shuffled()
                val originalCorrectAnswer = question.options[question.correctAnswerIndex]
                val newCorrectAnswerIndex = shuffledOptions.indexOf(originalCorrectAnswer)

                Question(
                    id = index + 1,
                    text = question.text,
                    options = shuffledOptions,
                    correctAnswerIndex = newCorrectAnswerIndex
                )
            }
    }

    private val easyQuestions = listOf(
        Question(1, "Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 1),
        Question(2, "What is the capital of France?", listOf("Berlin", "Madrid", "Paris", "Rome"), 2),
        Question(3, "How many colors are in a rainbow?", listOf("5", "6", "7", "8"), 2),
        Question(4, "What is the boiling point of water at sea level?", listOf("90°C", "100°C", "110°C", "120°C"), 1),
        Question(5, "Which gas do plants absorb during photosynthesis?", listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"), 1),
        Question(6, "Which ocean is the largest on Earth?", listOf("Atlantic", "Indian", "Arctic", "Pacific"), 3),
        Question(7, "How many continents are there on Earth?", listOf("5", "6", "7", "8"), 2),
        Question(8, "What is the chemical symbol for water?", listOf("CO2", "H2O", "O2", "NaCl"), 1),
        Question(9, "Which animal is known as the King of the Jungle?", listOf("Tiger", "Elephant", "Lion", "Bear"), 2),
        Question(10, "Which instrument has 88 keys?", listOf("Guitar", "Flute", "Violin", "Piano"), 3),
        Question(11, "How many days are in a leap year?", listOf("364", "365", "366", "367"), 2),
        Question(12, "What is the smallest prime number?", listOf("0", "1", "2", "3"), 2),
        Question(13, "Which country is home to the Kangaroo?", listOf("Brazil", "Australia", "South Africa", "Canada"), 1),
        Question(14, "Which shape has 3 sides?", listOf("Square", "Triangle", "Rectangle", "Hexagon"), 1),
        Question(15, "What is the color of an emerald?", listOf("Red", "Blue", "Green", "Yellow"), 2)
    )

    private val mediumQuestions = listOf(
        Question(1, "Which planet has the most moons in our solar system?", listOf("Jupiter", "Saturn", "Neptune", "Uranus"), 1),
        Question(2, "What is the main programming language used for Android development?", listOf("Java", "Kotlin", "Swift", "C++"), 1),
        Question(3, "Who painted the Mona Lisa?", listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"), 2),
        Question(4, "Which gas makes up the majority of Earth's atmosphere?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"), 1),
        Question(5, "In which year did the Titanic sink?", listOf("1905", "1912", "1918", "1923"), 1),
        Question(6, "What is the hardest natural substance on Earth?", listOf("Gold", "Iron", "Diamond", "Platinum"), 2),
        Question(7, "Which continent is the largest by land area?", listOf("North America", "Africa", "Europe", "Asia"), 3),
        Question(8, "What is the speed of light approximately?", listOf("150,000 km/s", "300,000 km/s", "450,000 km/s", "600,000 km/s"), 1),
        Question(9, "Which blood type is known as the universal donor?", listOf("A positive", "B negative", "O negative", "AB positive"), 2),
        Question(10, "Who discovered penicillin?", listOf("Marie Curie", "Alexander Fleming", "Louis Pasteur", "Isaac Newton"), 1),
        Question(11, "What is the capital city of Australia?", listOf("Sydney", "Melbourne", "Canberra", "Brisbane"), 2),
        Question(12, "How many bones are in an adult human body?", listOf("204", "206", "208", "210"), 1),
        Question(13, "Which planet is closest to the Sun?", listOf("Venus", "Earth", "Mercury", "Mars"), 2),
        Question(14, "What is the largest organ in the human body?", listOf("Heart", "Liver", "Skin", "Lungs"), 2),
        Question(15, "In Kotlin, which keyword is used to declare a read-only variable?", listOf("var", "val", "const", "let"), 1)
    )

    private val hardQuestions = listOf(
        Question(1, "What is the average time complexity of searching in a balanced Binary Search Tree?", listOf("O(1)", "O(n)", "O(log n)", "O(n log n)"), 2),
        Question(2, "Which chemical element has the atomic number 79?", listOf("Silver", "Gold", "Platinum", "Copper"), 1),
        Question(3, "In what year was the World Wide Web invented by Tim Berners-Lee?", listOf("1983", "1989", "1995", "2000"), 1),
        Question(4, "What is the capital city of Liechtenstein?", listOf("Vaduz", "Zurich", "Geneva", "Innsbruck"), 0),
        Question(5, "Which subatomic particle has a negative electric charge?", listOf("Proton", "Neutron", "Electron", "Quark"), 2),
        Question(6, "Who is credited with designing the Analytical Engine, the first mechanical computer?", listOf("Alan Turing", "Charles Babbage", "John von Neumann", "Ada Lovelace"), 1),
        Question(7, "What is the deepest known point in Earth's oceans?", listOf("Java Trench", "Puerto Rico Trench", "Challenger Deep", "Sunda Trench"), 2),
        Question(8, "Which galaxy is closest to the Milky Way?", listOf("Andromeda", "Triangulum", "Sombrero", "Centaurus A"), 0),
        Question(9, "In physics, what is the SI unit of electrical capacitance?", listOf("Ohm", "Farad", "Tesla", "Henry"), 1),
        Question(10, "Which human organ consumes approximately 20% of the body's energy?", listOf("Heart", "Liver", "Brain", "Kidney"), 2),
        Question(11, "What is the longest river in the world?", listOf("Amazon River", "Nile River", "Yangtze River", "Mississippi River"), 1),
        Question(12, "Who formulated the general theory of relativity?", listOf("Isaac Newton", "Albert Einstein", "Niels Bohr", "Max Planck"), 1),
        Question(13, "What is the most abundant gas in the universe?", listOf("Helium", "Oxygen", "Hydrogen", "Nitrogen"), 2),
        Question(14, "Which Kotlin feature allows extending a class with new functionality without inheriting from it?", listOf("Higher-Order Function", "Extension Function", "Sealed Class", "Inline Function"), 1),
        Question(15, "In Android, which lifecycle state comes immediately after onStart() when an activity becomes visible?", listOf("onCreate()", "onResume()", "onPause()", "onStop()"), 1)
    )
}
