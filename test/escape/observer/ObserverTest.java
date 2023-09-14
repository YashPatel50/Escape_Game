package escape.observer;

import org.junit.jupiter.api.Test;

import escape.EscapeGameBuilder;
import escape.EscapeGameManager;
import escape.EscapeGameManagerImp;
import escape.coordinate.Coordinate.CoordinateType;
import escape.exception.EscapeException;
import escape.gameobserver.GameObserver;
import escape.gameobserver.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class ObserverTest {

	
	@Test
	void testObserverCreation() {
		assertNotNull(new Observer());
	}
	
	@Test
	void testObserverNotify() {
		Observer obs = new Observer();
		obs.notify("Testing");
		assertEquals(obs.getMessage(), "Testing");
		
		obs.notify("Testing2", new EscapeException("Error"));
		assertEquals(obs.getMessage(), "Testing2: Error");
		
		obs.notify("Testing3", new EscapeException("Error", new Throwable()));
		assertEquals(obs.getMessage(), "Testing3: Error");
	}
	
	@Test
	void testAddObservers() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		Observer observer1 = new Observer();
		Observer observer2 = new Observer();
		
		assertEquals(observer1, manager.addObserver(observer1));
		assertEquals(observer2, manager.addObserver(observer2));
		assertNull(manager.addObserver(observer1));
	}
	
	@Test
	void testRemoveObservers() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder("config/egc/simpleSquare2.egc");
		EscapeGameManager manager = egb.makeGameManager();
		
		GameObserver observer1 = new Observer();
		GameObserver observer2 = new Observer();
		
		assertEquals(observer1, manager.addObserver(observer1));
		assertEquals(observer2, manager.addObserver(observer2));
		assertNull(manager.addObserver(observer1));
		
		assertEquals(observer2, manager.removeObserver(observer2));
		assertNull(manager.removeObserver(observer2));
	}
	
	@Test
	void testNotifyAll() throws Exception {
		EscapeGameManagerImp manager = new EscapeGameManagerImp(CoordinateType.SQUARE);
		
		Observer observer1 = new Observer();
		Observer observer2 = new Observer();
		
		manager.addObserver(observer1);
		manager.addObserver(observer2);
		
		manager.notifyAll("Testing notify all");
		assertEquals(observer1.getMessage(), "Testing notify all");
		assertEquals(observer2.getMessage(), "Testing notify all");
		
		manager.notifyAll("Testing notify all with exception", new Exception("error"));
		assertEquals(observer1.getMessage(), "Testing notify all with exception: error");
		assertEquals(observer2.getMessage(), "Testing notify all with exception: error");
	}
	
}
