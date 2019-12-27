package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Principal {
    public static void main(String[] args) {
        System.out.println(obtenerResultadoSegundoFuture());
        mostrarResultado(15);
        futurosCombinados("Groovy");
    }

    public static String obtenerResultadoSegundoFuture(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Empezando primer future");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                return "satisfactoriamente";
            }
        });
        CompletableFuture<String> future = completableFuture.thenApply(x -> {
            System.out.println("Empezando segundo future");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                return "Resultado del primer future:"+x;
            }
        });
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Hubo un error";
    }

    public static void mostrarResultado(int numero){
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Empezando tarea");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                return numero*numero;
            }
        });
        //thenAccept recibe el valor de completable y no retorna un valor
        CompletableFuture<Void> future = completableFuture.thenAccept(x -> {
            System.out.println("Resultado:"+x);
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Usar thenRun si no se quiere recibir como parametro el valor de retorno del CompletableFuture ni retornar un valor
    }

    public static void futurosCombinados(String nombre){
        CompletableFuture<Void> primerFuture = CompletableFuture.supplyAsync(() -> "La palabra ").thenCompose(x -> CompletableFuture.supplyAsync(() -> x + nombre + " tiene "))
                                                                                                 .thenAccept(x -> System.out.println(x + nombre.length()+" letras"));
        try {
            primerFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
