**Advanced Minecraft AntiCheat Base**

_This project was created so that any person who cannot create a good base for anti-cheat or is just lazy can instantly get a convenient and easy-to-use base._

```<---                    --->```

_Q: What is the advantage of this base?_

**A: Let's try to highlight the main ones:**
1) API. This base has ready to use Bukkit API, which contains a simple event flag system, and the ability to create your own check from another project.
```java
    @EventHandler
    public void onFlag(ACNameFlagEvent acEvent) {
        
        ACNameCheckAPI checkAPI = acEvent.getCheckAPI();
        
        System.out.println(
            String.format("player=%s, checkType=%s, checkName=%s", 
                checkAPI.getPlayer().getCustomName(),
                checkAPI.getCheckType(),
                checkAPI.getChatName()
            )
        );
        
        // Output: player=koloslolya, checkType=UTIL, checkName=Example (A)
    }

```
2) Command Manager. Convenient command creation system and ready-made language utils.
3) Theme Manager. The user can easily change any anti-cheat message at any time.
4) Packet Wrappers. Easy-to-understand AntiCheatEvent system, for which you do not need to know the entire MineCraft protocol.
5) Easy to change. The base, despite its convenience and practicality, is easily changed, at any time you can remake the base for yourself without changing other components of the project.

```<---                    --->```

_Q: What is the VL system?_

**A: Each check has its own violation value**

```<---                    --->```

_Q: How to add a Check?_

**A: Ah, it's simple:**
1) Create a class that extends Check.java
2) Create constructor:
```java
    public Example(Data data) {
        super(data /* Player Data */, 
              CheckType.UTIL /* Check type */, 
              "Example (A)" /* Check verbose name */, 
              "ExampleA" /* Config check name */, 
              DevelopmentState.DEVELOPMENT /* Development state of check */
        );
    }  
```
3) Override the handler method and make your check:
```java
    @Override
    public void process(AntiCheatEvent event) {
        if (event instanceof ClientFlying) {
            float pitch = ((ClientFlying) event).getTo().getPitch();
            if (pitch > 90) {
                flag("illegal pitch -> " + pitch);
            }
        }
    }
```

```<---                    --->```

Q: How to cache config values? I heard it is useful for optimization.

**A: Use ConfigCache.java for it!**

```java
    public static void tutorialValueCaching() {
    
        // Getting value from config with "isEasy" key
        String valueFromConfig = ConfigCache.Config.getString("isEasy");
        
        // ConfigCache.Language.getString() method automatically searches for a given theme
        String themeCachedValue = ConfigCache.Language.getString("tutorialValueCaching");
        
        // Output to spigot chat
        Bukkit.broadcastMessage(themeCachedValue.replace("%iseasy%, valueFromConfig));
        
        // Output: You ask, is it easy? The program will give the answer: true
        
    }

```

```<---                    --->```

Q: Should it only support 1.8?

**A: No. You can use any version of the spigot with this base without fear of incompatibility with the version**


```<---                    --->```

Q: And what are the dependencies of this project?

**A: You nee only latest Spigot jar and ProtocolLib for packet listeners.**

```<---                    --->```

Q: What about performance? There must be some problems?

A: **This base was tested with ~15 common checks with stress test higher than 75 players and have stable 20 TPS!**

```<---                    --->```

**To download you must just:**
1) Download project from github.
2) Rename ACName to your anticheat name _If you are using Intellij IDEA you can use Ctrl+Shift+R for it_
3) Compile it with Maven.
