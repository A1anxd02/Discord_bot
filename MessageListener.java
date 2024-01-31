package bot;
import tools.Message;

/**
 * TODO
 *
 * You are asked to build an interface called MessageListener.
 *
 * This interface has one method called onMessageReceived that returns
 * a String. It has a parameter of type Message.
 */
public interface MessageListener {
    String onMessageReceived(Message message);
}