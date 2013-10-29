## CitizensTraits
This class allows to manage the traits of the NPCs.

### array ctz\_npc\_has\_trait(npcID, traitName):
Returns if the NPC has a trait with the given name.

### array ctz\_npc\_traits(npcID, [traitName]):
Returns an associative array representing the traits the NPC has, or representing the trait with the given name. a trait is an associative array of one or more values, or null if the NOC does not have the trait. Actually, supported traits are OWNER.

### array ctz\_set\_npc\_trait(npcID, traitName, array):
Sets a NPC trait. The trait must be an associative array, in the same format that it is returned by ctz_npc_traits