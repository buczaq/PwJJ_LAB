package buczaq.spaceteam.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import buczaq.spaceteam.server.impl.GameServerImpl;
import buczaq.spaceteam.view.ServerWindow;

public class GameServerManager {
	
	public static final String REGISTRY_STUB_NAME = "GameServer";
	
	public static final int REGISTRY_PORT = 1099;
	
	Registry registry;

	private GameServerImpl server;

	private ServerWindow serverWindow;
	
	public GameServerManager(ServerWindow serverWindow) {
		
		this.serverWindow = serverWindow;
		
		try {
			registry = LocateRegistry.createRegistry(REGISTRY_PORT);
		} catch (RemoteException e) {
			System.err.println("Nie pobrano rejestru");
		}
	}
	
	public void startServer() throws RemoteException, AlreadyBoundException {

		server = new GameServerImpl(serverWindow);
		
		CrewGameServer stub = (CrewGameServer) UnicastRemoteObject.exportObject(server, 0);

		// Bind the remote object's stub in the registry
		registry.bind(REGISTRY_STUB_NAME, stub);
		
		System.out.println("Serwer podpięty do rejestru");
	}
	
	public void stopServer() throws AccessException, RemoteException, NotBoundException {
		
		registry.unbind(REGISTRY_STUB_NAME);

		System.out.println("Serwer odpięty od rejestru");
		
	}
}
