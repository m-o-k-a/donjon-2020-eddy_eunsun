package model.Difficulty;

public class SimpleDifficultyEnhance implements DifficultyStrategy {
    private int difficulty = 1;

    @Override
    public void doUpdateDifficulty() {
        difficulty++;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }
}
