import java.math.BigDecimal;

public class Neuron {
    private double weight = 0.5d; //изначально рандомный, в процесе обучения вес будеть изменяться
    private double lastError; //величина ошибки, изменяется после каждой итерации обучения
    public double smoothing = 0.00001d; //сглаживание изменения веса

    public double processInputData(double input){ //прямая конвертация
        return input * weight;
    }
    public double restoreInputData(double output){ //обратная конвертация
        return output / weight;
    }
    public void train(double input, double expectedResult){ //тренировка сети
        double actualResult = input * weight;
        lastError = expectedResult - actualResult;
        double correction = (lastError / actualResult) * smoothing;
        weight += correction;
    }

    public static void main(String[] args) {
        double km = 100d;
        double miles = 62.1371d;
        Neuron neuron = new Neuron();
        int i = 0;

        do{
            i++;
            neuron.train(km, miles);
            System.out.println("итерация номер: " + i + " ошибка " + neuron.lastError);

        }while (neuron.lastError > neuron.smoothing || neuron.lastError < -neuron.smoothing);

        System.out.println("Обучение завершино!");

        System.out.println(neuron.processInputData(100) + "миль в " + 100 + " километрах");
        System.out.println(neuron.processInputData(200) + "миль в " + 200 + " километрах");
        System.out.println(neuron.processInputData(541) + "миль в " + 541 + " километрах");
        System.out.println(neuron.restoreInputData(10) + "километров в " + 10 + " милях");


    }
}
