# Functions
## void ctz\_set\_npc\_protected(npcID, boolean):
Sets if the NPC is protected.
## void ctz\_set\_npc\_name(npcID, string):
Sets the name of the NPC.
## boolean ctz\_npc\_is\_protected(npcID):
Returns if the NPC is protected.
## boolean ctz\_entity\_is\_npc(entityID):
Returns if the given living entity is a NPC.
## boolean ctz\_despawn\_npc(npcID, [reason]):
Despawns the given NPC, the reason can be one of CHUNK_UNLOAD, DEATH, PENDING_RESPAWN, PLUGIN, REMOVAL, or WORLD_UNLOAD, PLUGIN by default. The reason is used by the ctz_npc_despawn event.
## void ctz\_remove\_npc(npcID):
Removes a NPC.
## array ctz\_all\_npcs():
Returns an array containing the ids of all registered NPCs.
## array ctz\_npc\_stored\_loc(npcID):
Returns the stored location of the given NPC (the last known location, or null if it has never been spawned).
## int ctz\_npc\_entity\_id(npcID):
Returns the entity id of the NPC, or null if the NPC is not spawned.
## string ctz\_npc\_full\_name(npcID):
Returns the full name of the NPC.
## void ctz\_set\_npc\_entity\_type(npcID, string):
Sets the entity type of the NPC. If the type is not a living entity type, the function will throw a BadEntityTypeException.
## void ctz\_set\_npc\_face\_loc(npcID, locationArray):
Forces the given NPC to face the given location if it is spawned.
## string ctz\_npc\_name(npcID):
Returns the name of the NPC.
## boolean ctz\_npc\_is\_spawned(npcID):
Returns if the given NPC is spawned.
## int ctz\_create\_npc([entityType], name | entityType, [id], name):
Creates a NPC and return its id, or null if the creation failed. entityType must be a living entity type, default to PLAYER. id takes a integer.
## boolean ctz\_spawn\_npc(npcID, locationArray):
Spawns the given NPC at the given location.
## int ctz\_npc\_id(entityID):
Returns the NPC id of the given living entity, or null if the entity is not a NPC.