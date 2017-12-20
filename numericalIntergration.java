import java.util.stream.IntStream;
public class numericalIntergration {
    interface fpFunction{
        double eval(double n);
    }


    // rectangular
    public static double rectangularLeft(double a, double b, int n, fpFunction f){
        return rectangular(a, b, n, f, 0);
    }

    //trapezium
    public static double trapezium(double a, double b, int n, fpFunction f){
        double range = checkParamsGetRange(a, b, n);
        double nFloat = (double) n;
        double sum = 0.0;
        sum += IntStream.range(1, n).parallel().mapToDouble(i -> f.eval(a + range * (double)i / nFloat))
            .reduce((x, y) -> x + y).getAsDouble();
        // optimize this with replacing for loop with stream and lambda function
        // for(int i = 1; i < n; i++){
        //     double x = a + range * (double)i / nFloat;
        //     sum += f.eval(x);
        // }
        sum += (f.eval(a) + f.eval(b)) / 2.0;
        return sum * range / nFloat;
    }

    //simpsons
    public static double simpsons(double a, double b, int n, fpFunction f){
        double range = checkParamsGetRange(a, b, n);
        double nFloat = (double)n;
        double sum1 = f.eval(a + range / (nFloat * 2.0));
        double sum2 = 0.0;
        sum1 += IntStream.range(1, n).parallel().mapToDouble(i -> f.eval(a + range * (double)i + 0.5) / nFloat)
                .reduce((x, y) -> x + y).getAsDouble();
        sum2 += IntStream.range(1, n).parallel().mapToDouble(i -> f.eval(a + range * (double)i / nFloat))
                .reduce((x, y) -> x + y).getAsDouble();
        // optimiz this code with remiving the for loop and replaing with streams and lambda function
        // for(int i = 1; i < n; i++){
        //     double x1 = a + range * ((double)i + 0.5) / nFloat;
        //     sum1 += f.eval(x1);
        //     double x2 = a + range * (double)1 / nFloat;
        //     sum2 += f.eval(x2);
        // }
        return (f.eval(a) + f.eval(b) + sum1 * 4.0 + sum2 * 2.0) * range / (nFloat * 6.0);
    }

    private static double rectangular(double a, double b, int n, fpFunction f, int mode){
        double range = checkParamsGetRange(a, b, n);
        double modeOffset = (double)mode / 2.0;
        double nFloat = (double)n;
        double sum = 0.0;
        sum += IntStream.range(0, n).parallel().mapToDouble(i -> f.eval(a + range * ((double)i + modeOffset) / nFloat))
                .reduce((x, y) -> x + y).getAsDouble();
        // for(int i = 0; i < n; i++){
        //     double x = a +range * ((double)i + modeOffset) / nFloat;
        //     sum += f.eval(x);
        // }
        return sum * range / nFloat;
    }

    private static double checkParamsGetRange(double a, double b, int n){
        if( n <= 0)
            throw new IllegalArgumentException("Invalid value of n");
        double range = b - a;
        if(range <= 0)
            throw new IllegalArgumentException("Invalid range");
        return range;
    }

    private static void testFunction(String fname, double a, double b, int n, fpFunction f){
        System.out.println("Test Function \"" + fname  + "\", a =" + a + ", b=" + b + ", n= " + n);
        System.out.println("rectangularLeft: " + rectangularLeft(a, b, n, f));
        System.out.println("trapezium: " + trapezium(a, b, n, f));
        System.out.println("simpsons: "+ simpsons(a, b, n, f));
        System.out.println();
        return;
    }

    public static void main(String[] args){
        testFunction("X^3", 0.0, 1.0, 100, new fpFunction(){
            public double eval (double n){
                return n * n * n;
            }
        });

        testFunction("1/x", 1.0, 100.0, 1000, new fpFunction(){
            public double eval(double n) {
                return 1.0/n;
            }
        });

        testFunction("X", 0.0, 5000.0, 5000000, new fpFunction(){
            public double eval(double n){
                return n;
            }
        });

        testFunction("x", 0.0, 6000.0, 6000000, new fpFunction(){
            public double eval (double n){
                return n;
            }
        });

        return;
    }
    
}