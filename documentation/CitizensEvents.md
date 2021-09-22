## CitizensEvents
Contains events related to the Citizens plugin.

### ctz\_npc\_despawn
Fires when an NPC despawns.
#### Prefilters
**reason**: <Macro> The reason the NPC is despawning  
**type**: <Macro> The entity type of the NPC  
**world**: <Macro>
#### Event Data
**reason**: The reason the NPC is despawning (one of CHUNK_UNLOAD, DEATH, PENDING_RESPAWN, PLUGIN, REMOVAL, or WORLD_UNLOAD)  
**npc**: The NPC id  
**entity**: The entityID of the NPC  
**type**: The entity type of the NPC  
**location**: The location where the NPC is
#### Mutable Fields

### ctz\_npc\_navigation\_cancel
Fires when an NPC's navigation is cancelled.
#### Prefilters
**cause**: <Macro> The cause of the cancellation  
**type**: <Macro> The entity type of the NPC  
**world**: <Macro>
#### Event Data
**npc**: The NPC id  
**entity**: the entityID of the NPC  
**type**: The entity type of the NPC  
**location**: The location where the NPC is (null if not spawned)  
**cause**: The cause of the cancellation (one of NPC_DESPAWNED, PLUGIN, REPLACE, STUCK, TARGET_DIED, or TARGET_MOVED_WORLD)
#### Mutable Fields

### ctz\_npc\_navigation\_complete
Fires when an NPC reaches its destination.
#### Prefilters
**type**: <Macro> The entity type of the NPC  
**world**: <Macro>
#### Event Data
**npc**: The NPC id  
**entity**: The entityID of the NPC  
**type**: The entity type of the NPC  
**location**: The location where the NPC is
#### Mutable Fields

### ctz\_npc\_spawn
Fires when an NPC spawns.
#### Prefilters
**type**: <Macro> The entity type of the NPC  
**world**: <Macro>
#### Event Data
**npc**: The NPC id  
**entity**: The entityID of the NPC  
**type**: The entity type of the NPC  
**location**: The location where the NPC will spawn
#### Mutable Fields

### ctz\_npc\_click
Fires when an NPC is clicked.
#### Prefilters
**button**: <Macro> Clicked button left or right  
**player**: <Macro>  
**world**: <Macro>  
**type**: <Macro>  
#### Event Data
**button**:  
**player**:  
**npc**: The NPC id  
**entity**: The entityID of the NPC  
**type**: The entity type of the NPC  
**location**: The location of the NPC
#### Mutable Fields