package waiter;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class WaiterPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public WaiterPanel() {
		setBackground(Color.BLUE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{176, 97, 0};
		gridBagLayout.rowHeights = new int[]{29, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnTable = new JButton("Table #5");
		btnTable.setBackground(Color.BLUE);
		GridBagConstraints gbc_btnTable = new GridBagConstraints();
		gbc_btnTable.insets = new Insets(0, 0, 0, 5);
		gbc_btnTable.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnTable.gridx = 0;
		gbc_btnTable.gridy = 4;
		add(btnTable, gbc_btnTable);

	}

}
