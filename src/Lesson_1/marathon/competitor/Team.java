package Lesson_1.marathon.competitor;

public class Team {
    private Competitor[] competitors; // Массив участников

    public Team(Competitor... competitors) {
        this.competitors = competitors;
    }

    public Competitor[] getCompetitors() {
        return competitors;
    }
}
