/**
 *
 * EventoZero - Advanced event factory and executor for Bukkit and Spigot.
 * Copyright © 2016 BlackHub OS and contributors.
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
package br.com.blackhubos.eventozero;

import br.com.blackhubos.eventozero.factory.EventFactory;
import br.com.blackhubos.eventozero.factory.EventHandler;
import java.io.File;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.blackhubos.eventozero.handlers.KitHandler;
import br.com.blackhubos.eventozero.handlers.ShopHandler;
import br.com.blackhubos.eventozero.storage.Storage;
import br.com.blackhubos.eventozero.util.Framework;
import br.com.blackhubos.eventozero.util.Framework.Configuration;
import br.com.blackhubos.eventozero.util.Framework.LoggerManager;

public final class EventoZero extends JavaPlugin
{

	private static LoggerManager<EventoZero> logger = null;
	private static Configuration config = null;
	private static Configuration config_rankings = null;
	private static Configuration config_points = null;
	private static Configuration config_bans = null;
	private static Configuration config_signs = null;
	private static Storage storage = null;

	private static ShopHandler shopHandler;
	private static KitHandler kitHandler;
        private static EventHandler eventHandler;

	@Override
	public void onEnable()
	{
		new Framework(); // Apenas carrega o WorldGuard e WorldEdit
		EventoZero.config = new Configuration(this, new File(this.getDataFolder(), "config.yml"));
		EventoZero.config_rankings = new Configuration(this, new File(this.getDataFolder(), "rankings.yml"));
		EventoZero.config_points = new Configuration(this, new File(this.getDataFolder(), "points.yml"));
		EventoZero.config_bans = new Configuration(this, new File(this.getDataFolder(), "bans.yml"));
		EventoZero.config_signs = new Configuration(this, new File(this.getDataFolder(), "signs.yml"));
		EventoZero.logger = new LoggerManager<EventoZero>(this, new File(this.getDataFolder(), "logs")).init(EventoZero.config.getString("tasks.savelogs"));

		for (final Configuration c : new Configuration[] { EventoZero.config, EventoZero.config_rankings, EventoZero.config_points, EventoZero.config_bans, EventoZero.config_signs })
		{
			if (c.copied())
			{
				this.getLogger().info(c.getFile() + " padrão copiada com sucesso..");
			}
		}

		EventoZero.shopHandler = new ShopHandler();
		EventoZero.kitHandler = new KitHandler();
                EventoZero.eventHandler = new EventHandler();
                
                EventFactory.loadEvents(this);
                
                kitHandler.loadKits(this);
                shopHandler.loadShops(this);

	}

	@Override
	public void onDisable()
	{
		// TODO: cancelar eventos ocorrendo
		// TODO: salvar scores se em flatfile; pois é necessário fazer flush do(s) arquivo(s)

		// Remove os listeners do plugin para ter melhor funcionamento com PluginManagers.
		HandlerList.unregisterAll(this);
	}
        
        public static void consoleMessage(String message){
            System.out.println("[EventoZero] " + message);
        }

	/**
	 * Dentro da classe EventoZero há uma variável estática carregada no <code>onEnable()</code> que representa a configuração do plugin, aceitando e usando o formato/charset
	 * utf8.
	 *
	 * @return Retorna uma {@link Configuration} vinda do arquivo config.yml
	 */
	@Override
	public Configuration getConfig()
	{
		return EventoZero.config;
	}

	@SuppressWarnings("unused")
	private void exemploRanking()
	{
		EventoZero.getStorage().depositPlayerRankingPoints("Atom", "Spleef", "vitorias", 1);
		EventoZero.getStorage().withdrawPlayerRankingPoints("atoM", "spleef", "vitorias", 1);
		// NOTA: spleef = Spleef, tal para Atom = atoM. Caso insensitive.
	}

	/**
	 *
	 * @return Retorna a instância do {@link ShopHandler}.
	 */
	public static ShopHandler getShopHandler()
	{
		return EventoZero.shopHandler;
	}

	/**
	 *
	 * @return Retorna a instância do {@link KitHandler}.
	 */
	public static KitHandler getKitHandler()
	{
		return EventoZero.kitHandler;
	}
        /**
         * 
         * @return Retorna instancia {@link EventHandler}.
         */
        public static EventHandler getEventHandler(){
                return EventoZero.eventHandler;
        }

	/**
	 * Dentro da classe EventoZero há uma variável estática carregada no <code>onEnable()</code> que representa a configuração do plugin, aceitando e usando o formato/charset
	 * utf8.
	 *
	 * @return Retorna uma {@link Configuration} vinda do arquivo config.yml
	 */
	public static Configuration getConfiguration()
	{
		return EventoZero.config;
	}

	/**
	 * @see {@link #getConfiguration()}
	 * @return Retorna uma {@link Configuration} vinda do arquivo points.yml
	 */
	public static Configuration getPointsConfiguration()
	{
		return EventoZero.config_points;
	}

	/**
	 * @see {@link #getConfiguration()}
	 * @return Retorna uma {@link Configuration} vinda do arquivo points.yml
	 */
	public static Configuration getRankingConfiguration()
	{
		return EventoZero.config_rankings;
	}

	/**
	 * @see {@link #getConfiguration()}
	 * @return Retorna uma {@link Configuration} vinda do arquivo signs.yml
	 */
	public static Configuration getSignConfiguration()
	{
		return EventoZero.config_signs;
	}

	/**
	 * @see {@link #getConfiguration()}
	 * @return Retorna uma {@link Configuration} vinda do arquivo bans.yml
	 */
	public static Configuration getBanConfiguration()
	{
		return EventoZero.config_bans;
	}

	/**
	 * Os logs ficam salvos em <code>EventoZero/logs/{data aqui}.txt</code>. Com esta variável, você pode adicionar novos informações aos logs.
	 *
	 * @return A instância única de {@link LoggerManager} para tratar logs.
	 */
	public static LoggerManager<EventoZero> getLoggerService()
	{
		return EventoZero.logger;
	}

	/**
	 * Obtém a implementação da classe Storage, para armazenamento de dados.
	 *
	 * @return A implementação da {@link Storage}.
	 */
	public static Storage getStorage()
	{
		return EventoZero.storage;
	}

}
