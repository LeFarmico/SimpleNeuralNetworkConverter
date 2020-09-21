# SimpleNeuralNetworkConverter
Для того, чтобы начать использовать нейронное обучения, надо этот нейрон создать. Для этого создаем класс Neuron. В котором определяем 3 переменные

    private double weight = 0.5d;
    private double lastError;
    public double smoothing = 0.00001d; 
    
`weigth` - это переменная отвечающая за вес нейрона, в зависимости от которого и будет приниматься решение, правильно ли произошла конвертация.
На данный момент, это число подобрано случайно, в процессе обучения нейрона, оно будет изменяться.
Чтобы его изменить надо определиться с точностью вычисления, для этого используется переменная `smoothing`, для вычисление величины текущей ошибки, используется переменная `lastError`.
Когда величина ошибки будет меньше величины погрешности ( `lastError < smoothing` ), тогда нейрон завершит обучение.

## Обучение нейрона
Для обучения нейрона используется метод `Train`, принимающий в себя исходное значение `input` и ожидаемую конвертацию `expectedResult`. 

    public void train(double input, double expectedResult)

Далее вычисляется результат конвертации при текущем весе. Это позволит нам вычислить размер ошибки `lastError`.

    double actualResult = input * weight;
    lastError = expectedResult - actualResult;    

Далее нужно скорректировать вес, чтобы уменьшить величину ошибки. Для это используется переменная `correction`, Вычисляется она путем деления величины ошибки на ожидаемый результат.
Для того, чтобы уменьшить шаг изменения веса, умножается результат на величину сглаживания. 
Последним этапом корректируется вес на величину `correction`.

    lastError = expectedResult - actualResult;
    double correction = (lastError / actualResult) * smoothing;
    weight += correction;

## Конвертация
Для конвертации используются два метода. Прямаи и обратная конвертация.

    public double processInputData(double input){ //прямая конвертация
        return input * weight;
    }
    public double restoreInputData(double output){ //обратная конвертация
        return output / weight;
    }

## Цикл обучения
Обучение происходит в цикле, после каждой итерации которого, изменяется вес до тех пор, пока величина ошибки не станет меньше величины погрешности. 
Для примера используем конвертацию километров в мили

    do{
          i++;
          neuron.train(km, miles);
          System.out.println("итерация номер: " + i + " ошибка " + neuron.lastError);

      }while (neuron.lastError > neuron.smoothing || neuron.lastError < -neuron.smoothing);

## Пример

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
        System.out.println(neuron.restoreInputData(10) + " километров в " + 10 + " милях");

В итоге мы получаем правильный результат:

    итерация номер: 858347 ошибка 1.0000061898551849E-5
    итерация номер: 858348 ошибка 9.999900960622199E-6
    Обучение завершино!
    62.137090000259974миль в 100 километрах
    124.27418000051995миль в 200 километрах
    336.16165690140645миль в 541 километрах
    16.09344756884843 километров в 10 милях
