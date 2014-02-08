package net.s5games.mafia.services;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Dec 26, 2008
 * Time: 7:31:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UndoableMudCommand extends MudCommand {
    public void undo();
}
