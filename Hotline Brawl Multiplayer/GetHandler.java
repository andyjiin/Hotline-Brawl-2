public class GetHandler{
  private Handler handler;
  
  public GetHandler(Handler handler){
    this.handler=handler;
  }
  
  public Handler getHandler(){
    return handler;
  }
  
  public void setHandler(Handler handler){
    this.handler=handler;
  }
}