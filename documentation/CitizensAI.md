# CitizensAI
## void ctz\_set\_npc\_avoid\_water(npcID, boolean):
Sets if the given NPC has to avoid water.
## void ctz\_set\_npc\_base\_speed(npcID, double):
Sets the base speed of the given NPC.
## void ctz\_set\_npc\_distance\_margin(npcID, double):
Sets the distance margin of the given NPC.
## void ctz\_set\_npc\_range(npcID, double):
Sets the range of the given NPC.
## double ctz\_npc\_speed(npcID):
Returns the speed of the given NPC.
## double ctz\_npc\_distance\_margin(npcID):
Returns the distance margin of the given NPC.
## void ctz\_npc\_speak(npcID, message, [recipients]):
Makes the given NPC speak a message to the given recipients. recipients can take an array of player names and/or living entity ids, an int (living entity id), or a string (player name).
## void ctz\_set\_npc\_speed\_modifier(npcID, double):
Sets the speed modifier of the given NPC.
## void ctz\_set\_npc\_target(npcID, target, [isAggressive]):
Sets the target of the given NPC. target can be a location array or an entityID. If target is an entityID, isAggressive (a boolean) will specify if the NPC will attack the targeted entity, defaut to false.
## boolean ctz\_npc\_is\_navigating(npcID):
Returns if the given NPC is navigating.
## double ctz\_npc\_base\_speed(npcID):
Returns the base speed of the given NPC.
## string ctz\_npc\_full\_name(npcID):
Returns the full name of the NPC.
## boolean ctz\_npc\_is\_aggressive(npcID):
Returns if the given NPC is aggressive.
## void ctz\_set\_npc\_stationary\_ticks(npcID, int):
Sets the stationary ticks of the given NPC.
## int ctz\_npc\_stationary\_ticks(npcID):
Returns the stationary ticks of the given NPC.
## double ctz\_npc\_range(npcID):
Returns the range of the given NPC.
## void ctz\_cancel\_npc\_navigation(npcID):
Cancels the current navigation of the given NPC towards a target.
## mixed ctz\_npc\_target(npcID, [field]):
Returns the target of the given NPC. Field can be one of 'location', 'entity' or 'type', default to 'type'. If field is 'location', the function will returns a location array, else if field is 'entity', the function fill return the entity id, else if field is 'type', the function will return the target type (can be ENTITY, LOCATION, or NONE). The function will return null if the NPC has not a target, or if the field is 'entity' and that the target type is a location.
## boolean ctz\_npc\_avoid\_water(npcID):
Returns if the given NPC has to avoid water.
## double ctz\_npc\_speed\_modifier(npcID):
Returns the speed modifier of the given NPC.