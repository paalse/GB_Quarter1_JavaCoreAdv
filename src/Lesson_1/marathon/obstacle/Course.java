package Lesson_1.marathon.obstacle;

import Lesson_1.marathon.competitor.Competitor;
import Lesson_1.marathon.competitor.Team;

public class Course {
    private Obstacle[] course;

    public Course(Obstacle... course) {
        this.course = course;
    }

    /**
     * Запуск марафона
     *
     * @param team - команда
     */
    public void start(Team team) {
        for (Competitor c : team.getCompetitors()) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
        for (Competitor c : team.getCompetitors()) {
            c.info();
        }
    }
}

