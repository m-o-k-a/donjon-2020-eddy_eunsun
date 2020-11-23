package view.draw;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Dungeon.Dungeon;
import model.Entity.Player;
import model.Room.Chamber;
import model.Room.ExitRoom;
import model.Room.Wall;

public class MiniMap implements Drawable {
    private Canvas minimap;
    private int cellSize;
    private Player player;
    private Dungeon dungeon;

    public MiniMap(Canvas minimap,Player player, Dungeon dungeon, int cellSize) {
        this.cellSize = cellSize;
        this.minimap = minimap;
        this.player = player;
        this.dungeon = dungeon;
        drawMiniMap(minimap, player, cellSize, dungeon);
    }

    public void draw() {
        updateDrawMiniMap(minimap, player, cellSize, dungeon);
    }

    private void drawMiniMap(Canvas minimap, Player player, int roomSize, Dungeon dungeon) {
        Color color = Color.BLACK;
        int cellSize = (int) (minimap.getWidth()/roomSize);
        for(int width = 0; width<roomSize; width++) {
            for(int height = 0; height<roomSize; height++) {
                if(dungeon.getRoom(width, height) instanceof Chamber) {
                    if(player.x == width && player.y == height) { color = Color.LIME; }
                    else if(((Chamber) dungeon.getRoom(width, height)).isVisited) { color = Color.GRAY; }
                    else { color = Color.BLACK; }
                }
                drawMiniMapCell(minimap, color, cellSize, width, height);
                drawWalls(dungeon, player.x, player.y);
                drawExit(dungeon, player.x, player.y);
            }
        }
    }

    private void updateDrawMiniMap(Canvas minimap, Player player, int roomSize, Dungeon dungeon) {
        int cellSize = (int) (minimap.getWidth()/roomSize);
        for(int width = 0; width<roomSize; width++) {
            for(int height = 0; height<roomSize; height++) {
                if(dungeon.getRoom(width, height) instanceof Chamber) {
                    if(((Chamber) dungeon.getRoom(width, height)).isVisited) {
                        drawMiniMapCell(minimap, Color.GRAY, cellSize, width, height);
                    }
                }
            }
        }
        drawMiniMapCell(minimap, Color.LIME, cellSize, player.x, player.y);
        drawWalls(dungeon, player.x, player.y);
        drawExit(dungeon, player.x, player.y);
    }

    private void drawMiniMapCell(Canvas minimap,Color color, int cellSize, int width, int height) {
        minimap.getGraphicsContext2D().setFill(color);
        minimap.getGraphicsContext2D().fillRect(width*cellSize, height*cellSize, cellSize-1, cellSize-1);
    }

    private void drawWalls(Dungeon dungeon, int x, int y) {
        if(dungeon.getRoom(x-1, y) instanceof Wall && x>0) { drawMiniMapCell(minimap, Color.DARKBLUE, (int)(minimap.getWidth()/cellSize), x-1, y);}
        if(dungeon.getRoom(x, y-1) instanceof Wall && y>0) { drawMiniMapCell(minimap, Color.DARKBLUE, (int)(minimap.getWidth()/cellSize), x, y-1);}
        if(dungeon.getRoom(x+1, y) instanceof Wall && x<cellSize-1) { drawMiniMapCell(minimap, Color.DARKBLUE, (int)(minimap.getWidth()/cellSize), x+1, y);}
        if(dungeon.getRoom(x, y+1) instanceof Wall && y<cellSize-1) { drawMiniMapCell(minimap, Color.DARKBLUE, (int)(minimap.getWidth()/cellSize), x, y+1);}
    }

    private void drawExit(Dungeon dungeon, int x, int y) {
        if(dungeon.getRoom(x-1, y) instanceof ExitRoom) { drawMiniMapCell(minimap, Color.GOLD, (int)(minimap.getWidth()/cellSize), x-1, y);}
        else if(dungeon.getRoom(x, y-1) instanceof ExitRoom) { drawMiniMapCell(minimap, Color.GOLD, (int)(minimap.getWidth()/cellSize), x, y-1);}
        else if(dungeon.getRoom(x+1, y) instanceof ExitRoom) { drawMiniMapCell(minimap, Color.GOLD, (int)(minimap.getWidth()/cellSize), x+1, y);}
        else if(dungeon.getRoom(x, y+1) instanceof ExitRoom) { drawMiniMapCell(minimap, Color.GOLD, (int)(minimap.getWidth()/cellSize), x, y+1);}
    }
}
