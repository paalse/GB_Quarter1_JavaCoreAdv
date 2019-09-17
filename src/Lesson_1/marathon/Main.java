package Lesson_1.marathon;

import Lesson_1.marathon.competitor.Cat;
import Lesson_1.marathon.competitor.Dog;
import Lesson_1.marathon.competitor.Human;
import Lesson_1.marathon.competitor.Team;
import Lesson_1.marathon.obstacle.Course;
import Lesson_1.marathon.obstacle.Cross;
import Lesson_1.marathon.obstacle.Wall;

public class Main {
    public static void main(String[] args) {
        Team team = new Team(new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"));
        Course course = new Course(new Cross(80), new Wall(2), new Wall(1), new Cross(120));
        course.start(team);
    }
}