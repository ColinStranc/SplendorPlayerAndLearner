# SplendorPlayerAndLearner

### TODO List

- Standarize a Project Structure
  - What different projects do we have?
- Work out distinction between Splendor and SplendorService.
  - They need to be merged and then have a new useful separation pulled out. It may be as simple as a rename and a tiny
  functional change
  - This is highlighting the fact that the API is a bit weird. We want similar handling of the different actions (such 
  as updating state, active turn index, saving actions...) across all functions, but they have no common definition 
  forced upon them at the moment.
- Make an Exception strategy
- Integration testing
- Logging
- Database connection
- Console Player
- A* player
- Evolutionary A

### Database Rough Schema

#### Game

- id
- settings
- activeStateHistoryId


#### StateHistory

- id
- activeStateId
- previousStateHistoryId

### Analysis Questions

- How much does the non-tier 1 decks affect the first move?
- How often should you target the VP tier 1 cards early