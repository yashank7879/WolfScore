package com.wolfscore.statsModal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

	@JsonProperty("data")
	public Data data;

	@JsonProperty("meta")
	private Meta meta;

	@JsonProperty("commentaries")
	private boolean commentaries;

	@JsonProperty("localTeam")
	private LocalTeam localTeam;

	@JsonProperty("scores")
	private Scores scores;

	@JsonProperty("visitorteam_id")
	private int visitorteamId;

	@JsonProperty("weather_report")
	private WeatherReport weatherReport;

	@JsonProperty("standings")
	private Standings standings;

	@JsonProperty("colors")
	private Colors colors;

	@JsonProperty("leg")
	private String leg;

	@JsonProperty("round_id")
	private int roundId;

	@JsonProperty("referee_id")
	private int refereeId;

	@JsonProperty("stats")
	public Stats stats;

	@JsonProperty("localteam_id")
	private int localteamId;

	@JsonProperty("id")
	private int id;

	@JsonProperty("pitch")
	private String pitch;

	@JsonProperty("venue_id")
	private int venueId;

	@JsonProperty("assistants")
	private Assistants assistants;

	@JsonProperty("visitorTeam")
	private VisitorTeam visitorTeam;

	@JsonProperty("stage_id")
	private int stageId;

	@JsonProperty("season_id")
	private int seasonId;

	@JsonProperty("coaches")
	private Coaches coaches;

	@JsonProperty("aggregate_id")
	private Object aggregateId;

	@JsonProperty("winning_odds_calculated")
	private boolean winningOddsCalculated;

	@JsonProperty("deleted")
	private boolean deleted;

	@JsonProperty("formations")
	private Formations formations;

	@JsonProperty("group_id")
	private Object groupId;

	@JsonProperty("time")
	private Time time;

	@JsonProperty("league_id")
	private int leagueId;

	@JsonProperty("attendance")
	private Object attendance;

	@JsonProperty("twitter")
	private Object twitter;

	@JsonProperty("logo_path")
	private String logoPath;

	@JsonProperty("name")
	private String name;

	@JsonProperty("founded")
	private int founded;

	@JsonProperty("legacy_id")
	private int legacyId;

	@JsonProperty("national_team")
	private boolean nationalTeam;

	@JsonProperty("current_season_id")
	private Object currentSeasonId;

	@JsonProperty("country_id")
	private int countryId;

	@JsonProperty("short_code")
	private Object shortCode;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setCommentaries(boolean commentaries){
		this.commentaries = commentaries;
	}

	public boolean isCommentaries(){
		return commentaries;
	}

	public void setLocalTeam(LocalTeam localTeam){
		this.localTeam = localTeam;
	}

	public LocalTeam getLocalTeam(){
		return localTeam;
	}

	public void setScores(Scores scores){
		this.scores = scores;
	}

	public Scores getScores(){
		return scores;
	}

	public void setVisitorteamId(int visitorteamId){
		this.visitorteamId = visitorteamId;
	}

	public int getVisitorteamId(){
		return visitorteamId;
	}

	public void setWeatherReport(WeatherReport weatherReport){
		this.weatherReport = weatherReport;
	}

	public WeatherReport getWeatherReport(){
		return weatherReport;
	}

	public void setStandings(Standings standings){
		this.standings = standings;
	}

	public Standings getStandings(){
		return standings;
	}

	public void setColors(Colors colors){
		this.colors = colors;
	}

	public Colors getColors(){
		return colors;
	}

	public void setLeg(String leg){
		this.leg = leg;
	}

	public String getLeg(){
		return leg;
	}

	public void setRoundId(int roundId){
		this.roundId = roundId;
	}

	public int getRoundId(){
		return roundId;
	}

	public void setRefereeId(int refereeId){
		this.refereeId = refereeId;
	}

	public int getRefereeId(){
		return refereeId;
	}

	public void setStats(Stats stats){
		this.stats = stats;
	}

	public Stats getStats(){
		return stats;
	}

	public void setLocalteamId(int localteamId){
		this.localteamId = localteamId;
	}

	public int getLocalteamId(){
		return localteamId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPitch(String pitch){
		this.pitch = pitch;
	}

	public String getPitch(){
		return pitch;
	}

	public void setVenueId(int venueId){
		this.venueId = venueId;
	}

	public int getVenueId(){
		return venueId;
	}

	public void setAssistants(Assistants assistants){
		this.assistants = assistants;
	}

	public Assistants getAssistants(){
		return assistants;
	}

	public void setVisitorTeam(VisitorTeam visitorTeam){
		this.visitorTeam = visitorTeam;
	}

	public VisitorTeam getVisitorTeam(){
		return visitorTeam;
	}

	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public int getStageId(){
		return stageId;
	}

	public void setSeasonId(int seasonId){
		this.seasonId = seasonId;
	}

	public int getSeasonId(){
		return seasonId;
	}

	public void setCoaches(Coaches coaches){
		this.coaches = coaches;
	}

	public Coaches getCoaches(){
		return coaches;
	}

	public void setAggregateId(Object aggregateId){
		this.aggregateId = aggregateId;
	}

	public Object getAggregateId(){
		return aggregateId;
	}

	public void setWinningOddsCalculated(boolean winningOddsCalculated){
		this.winningOddsCalculated = winningOddsCalculated;
	}

/*	public boolean isWinningOddsCalculated(){
		return winningOddsCalculated;
	}

	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}

	public boolean isDeleted(){
		return deleted;
	}

	public void setFormations(Formations formations){
		this.formations = formations;
	}

	public Formations getFormations(){
		return formations;
	}

	public void setGroupId(Object groupId){
		this.groupId = groupId;
	}

	public Object getGroupId(){
		return groupId;
	}

	public void setTime(Time time){
		this.time = time;
	}

	public Time getTime(){
		return time;
	}

	public void setLeagueId(int leagueId){
		this.leagueId = leagueId;
	}

	public int getLeagueId(){
		return leagueId;
	}

	public void setAttendance(Object attendance){
		this.attendance = attendance;
	}

	public Object getAttendance(){
		return attendance;
	}

	public void setTwitter(Object twitter){
		this.twitter = twitter;
	}

	public Object getTwitter(){
		return twitter;
	}

	public void setLogoPath(String logoPath){
		this.logoPath = logoPath;
	}

	public String getLogoPath(){
		return logoPath;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFounded(int founded){
		this.founded = founded;
	}

	public int getFounded(){
		return founded;
	}

	public void setLegacyId(int legacyId){
		this.legacyId = legacyId;
	}

	public int getLegacyId(){
		return legacyId;
	}

	public void setNationalTeam(boolean nationalTeam){
		this.nationalTeam = nationalTeam;
	}

	public boolean isNationalTeam(){
		return nationalTeam;
	}

	public void setCurrentSeasonId(Object currentSeasonId){
		this.currentSeasonId = currentSeasonId;
	}

	public Object getCurrentSeasonId(){
		return currentSeasonId;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	public void setShortCode(Object shortCode){
		this.shortCode = shortCode;
	}

	public Object getShortCode(){
		return shortCode;
	}*/
}