package model.Difficulty;

public class SimpleDifficultyEnhance implements DifficultyStrategy {
    private int difficulty;
    private int cellSize;

    public SimpleDifficultyEnhance(int difficulty, int cellSize) {
        this.difficulty = difficulty;
        this.cellSize = cellSize;
    }

    @Override
    public void doUpdateDifficulty() {
        difficulty++;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public int getCellSize() { return cellSize; }
}
