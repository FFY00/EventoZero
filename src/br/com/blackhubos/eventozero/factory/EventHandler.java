/**
 *
 * EventoZero - Advanced event factory and executor for Bukkit and Spigot.
 * Copyright � 2016 BlackHub OS and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.blackhubos.eventozero.factory;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.entity.Player;

public class EventHandler {

    private final Set<Event> events;

    public EventHandler() {
        this.events = new HashSet<>();
    }

    public Event getEventByName(final String name) {
        for (Event e : getEvents()) {
            if (e.getEventName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public Event getEventByPlayer(final Player player) {
        for (Event e : getEvents()) {
            if (e.hasPlayerJoined(player)) {
                return e;
            }
        }
        return null;
    }

    public Set<Event> getEvents() {
        return this.events;

    }

    public void loadEvent(final Event event) {
        if (event != null) {
            throw new NullArgumentException("Event is null");
        }
        events.add(event);
    }

}
