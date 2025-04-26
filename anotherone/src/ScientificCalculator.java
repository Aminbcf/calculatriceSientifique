import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScientificCalculator {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                CalculatorModel model = new CalculatorModel();
                CalculatorView view = new CalculatorView();
                CalculatorController controller = new CalculatorController(model, view);

                view.show();
            });
        }

}
