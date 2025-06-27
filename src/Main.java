public class Main {

    static void callRemoteService() throws Exception {
        if (Math.random() > 0.3)
            throw new RuntimeException("Service unavailable");
        System.out.println("Service succeeded.");
    }

    static void getServerMsg() {
        if(Math.random() > 0.3)
            throw new RuntimeException("No messages yet!");
        System.out.println("MSG: Hellooo!!");
    }

    public static void main(String[] args) throws InterruptedException {

//        Retry logic (e.g., exponential backoff)
        int attempt = 0, maxRetries = 5, delay = 1000;
        while(attempt < maxRetries){
            try{
                System.out.println("Attempting to call...");
                callRemoteService();
                break;
            } catch(Exception e){
                System.out.println("FAILED: "+e.getMessage());
                attempt++;
                if(attempt < maxRetries){
                    try{
                        Thread.sleep(delay);
                    }
                    catch(InterruptedException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                else {
                    System.out.println("You have reached the max attempts");
                }

            }
        }

        System.out.println("------------------");
//        Polling mechanisms
//        SHORT POLLING -- Server-Client communication, client pings the server every 1sec for any message
        while(true){
            try {
                System.out.println("Hey server, is there any msg for me??");
                getServerMsg();
                break;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("------------------");
//       Progress Indicator (Console Spinner)
        char[] spinner = {'|', '/', '-', '\\'};
        for(int i=0; i < 10; i++){
            System.out.print(spinner[i% spinner.length]);
            Thread.sleep(1000);
        }
        System.out.println("DONE!    ");

    }
}