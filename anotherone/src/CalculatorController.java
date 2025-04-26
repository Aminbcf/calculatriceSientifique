import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController implements ActionListener {
    private CalculatorModel model;
    private CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;

        this.view.addCalculationListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String currentExpression = view.getExpression();

        switch (command) {
            case "=":
                try {
                    model.setExpression(currentExpression);
                    model.calculate();
                    view.setExpression(Double.toString(model.getResult()));
                } catch (ArithmeticException ex) {
                    view.displayError("Error: " + ex.getMessage());
                }
                break;
            case "Clear":
                model.clear();
                view.setExpression("");
                break;
            default:
                view.setExpression(currentExpression + command);
                break;
        }
    }
}