package game.tiger;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.gameMain.BaseScenarioGameContainer;

/**
 * シナリオゲーム用画面
 * @author Nitta
 *
 */
public class ScenarioGameContainer extends BaseScenarioGameContainer {

	public ScenarioGameContainer() {
		super();
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		super.build(gc);
		canvas.setRelativePosition(0.0f, 0.0f);		// 3D表示部の左上端(ここを書き換えると右端のダイアログが変わる)
		canvas.setRelativeSize(0.8f, 1.0f);		// 3D表示部のサイズ
		addCanvas(canvas);
		dialog.setRelativePosition(0.8f, 0.75f);	// ダイアログの左上端(ここを書き換えると右端のダイアログが変わる)
		dialog.setFont(new Font("", Font.PLAIN, 12));	// 文字のフォント
		dialog.setColor(Color.WHITE);				// 文字の色
		addWidget(dialog);
		repaint();
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		if (key.getPlayer() == 0 && key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			scenario.fire("A");
		}
	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
	}
}
