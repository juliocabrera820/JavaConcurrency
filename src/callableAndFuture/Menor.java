package callableAndFuture;

import java.util.List;
import java.util.concurrent.Callable;

public class Menor implements Callable<Integer> {
    private List<Integer> numeros;

    public Menor(List<Integer> numeros) {
        this.numeros = numeros;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        return numeros.stream().min(Integer::compareTo).get();
    }
}
