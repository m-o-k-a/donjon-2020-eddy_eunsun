package view.draw;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LogsDrawable implements Drawable {

    private TextFlow textLogsContent;

    public LogsDrawable(TextFlow textLogsContent) {
        this.textLogsContent = textLogsContent;
    }

    /**
     * method that will add a log to the textFlow textLogsContent of the interface
     * @param logs
     */
    public void addLogs(Color color, Text... logs) {
        for(Text log : logs) {
            log.setFill(color);
            textLogsContent.getChildren().add(0, log);
        }
    }

    @Override
    public void draw() {}
}
