package me.snipz.locales

import me.snipz.locales.objects.LocaleConfig
import me.snipz.locales.objects.LocaleEnum
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.Plugin
import java.io.File

object LocalesRegistry {

    var messageDesigner = { message: String -> MiniMessage.miniMessage().deserialize(message) }
        private set
    private var initialized = false

    fun messageDesigner(messageDesigner: (String) -> Component) {
        this.messageDesigner = messageDesigner
    }

    inline fun <reified E> buildLocaleConfig(plugin: Plugin): LocaleConfig<E> where E : Enum<E>, E : LocaleEnum {
        val file = File(plugin.dataFolder, "locale.yml")
        if (!file.exists()) {
            if (plugin.getResource("locale.yml") != null) {
                plugin.saveResource("locale.yml", true)
            } else {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
        }

        val config = LocaleConfig(file, E::class.java)
        return config
    }
}