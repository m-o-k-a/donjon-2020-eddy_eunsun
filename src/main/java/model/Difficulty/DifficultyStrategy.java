package model.Difficulty;

public interface DifficultyStrategy {
    void doUpdateDifficulty();
    int getDifficulty();
    int getCellSize();
}
