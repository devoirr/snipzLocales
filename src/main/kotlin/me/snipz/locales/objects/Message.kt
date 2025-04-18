package me.snipz.locales.objects

import me.snipz.locales.LocalesRegistry
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

class Message(private val lines: List<String>) {

    private val singleLine = lines.joinToString(" ")

    fun send(sender: CommandSender) {
        lines.map { LocalesRegistry.messageDesigner(it) }
            .forEach { line -> sender.sendMessage(line) }
    }

    fun send(sender: CommandSender, replacements: Map<String, String>) {
        lines.map {
                var text = it
                replacements.forEach { replacement ->
                    text = text.replace(replacement.key, replacement.value)
                }
                return@map text
            }
            .map { LocalesRegistry.messageDesigner(it) }
            .forEach { line -> sender.sendMessage(line) }
    }

    fun send(sender: CommandSender, vararg replacements: Pair<String, String>) {
        lines.map {
                var text = it
                replacements.forEach { replacement ->
                    text = text.replace(replacement.first, replacement.second)
                }
                return@map text
            }
            .map { LocalesRegistry.messageDesigner(it) }
            .forEach { line -> sender.sendMessage(line) }
    }

    fun sendActionBar(player: Player) {
        player.sendActionBar(LocalesRegistry.messageDesigner(singleLine))
    }

    fun sendActionBar(player: Player, vararg replacements: Pair<String, String>) {
        var line = singleLine
        replacements.forEach { replacement ->
            line = line.replace(replacement.first, replacement.second)
        }

        player.sendActionBar {
            LocalesRegistry.messageDesigner(line)
        }
    }

    fun isSingleLine() = lines.size == 1

    fun write(config: FileConfiguration, key: String) {
        if (isSingleLine()) {
            config.set(key, lines.first())
        } else {
            config.set(key, lines)
        }
    }

}