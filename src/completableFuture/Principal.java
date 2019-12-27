package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Principal {
    public static void main(String[] args) {
        System.out.println(obtenerResultadoSegundoFuture());
        mostrarResultado(15);
        futurosCombinados("Groovy");
        enviarFuturos("Kotlin");
        multiplesFuturos();
    }

    public static String obtenerResultadoSegundoFuture(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Satisfactoriamente");
        CompletableFuture<String> future = completableFuture.thenApply(x -> "Resultado del primer future:"+x);
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
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> numero*numero);
        CompletableFuture<Void> future = completableFuture.thenAccept(x -> System.out.println("Resultado:"+x));
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

    public static void enviarFuturos(String nombre){
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "La palabra " + nombre).thenAcceptBoth(CompletableFuture.supplyAsync(()-> " tiene " + nombre.length() + " letras"),
                                                                                                    (x,y) -> System.out.println(x+y));
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void multiplesFuturos(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("Future 1");
            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() ->{
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("Future 2");
            }
        });
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("Future 3");
            }
        });
        CompletableFuture<Void> futuros = CompletableFuture.allOf(future,future2,future3);
        try {
            futuros.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
