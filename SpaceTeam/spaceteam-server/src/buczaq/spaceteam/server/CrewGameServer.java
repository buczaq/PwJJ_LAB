package buczaq.spaceteam.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import buczaq.spaceteam.server.command.Command;
import buczaq.spaceteam.server.impl.GameState;

public interface CrewGameServer extends Remote {
    boolean connectCrew(CrewType crewType, List<Command> randomCommands) throws RemoteException;

	GameState getState() throws RemoteException;

	Command getCurrentCommand() throws RemoteException;

	GameState onExecutedCorrectly() throws RemoteException;

	GameState onExecutedIncorrectly() throws RemoteException;
	
	int getCurrentTeamScore() throws RemoteException;
    
}
