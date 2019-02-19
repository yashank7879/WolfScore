package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data1 {
    private Venue venue;
    private Cards cards;
    private Scores scores;
    private Referee referee;
    private Integer visitorteamId;
    private WeatherReport weatherReport;
    private Standings standings;
    private Colors colors;
    private Corners corners;
    private Substitutions substitutions;
    private Stats stats;
    private Odds odds;
    private FlatOdds flatOdds;

    private Events events;
    private League league;
    public Time time;
    public int attendance;


    public Boolean commentaries;
    private LocalTeam localTeam;
    private Lineup lineup;
    private Integer visitorteam_id;
    private String leg;
    private Integer roundId;
    private Integer refereeId;
    private Integer localteam_id;
    private Season season;
    private Integer id;
    private String pitch;
    private Integer venueId;
    private Formations formations;
    @JsonProperty("visitorTeam")
    public VisitorTeam visitorTeam;

    @JsonProperty("localCoach")
    public Player localCoach;

    @JsonProperty("visitorCoach")
    public Player visitorCoach;

    @JsonProperty("bench")
    public com.wolfscore.aboutMatch.Bench bench;


    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
    //  private VisitorTeam visitorTeam;


    public Boolean getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(Boolean commentaries) {
        this.commentaries = commentaries;
    }

    public LocalTeam getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(LocalTeam localTeam) {
        this.localTeam = localTeam;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Cards getCards() {
        return cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public Lineup getLineup() {
        return lineup;
    }

    public void setLineup(Lineup lineup) {
        this.lineup = lineup;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Integer getVisitorteamId() {
        return visitorteamId;
    }

    public void setVisitorteamId(Integer visitorteamId) {
        this.visitorteamId = visitorteamId;
    }

    public WeatherReport getWeatherReport() {
        return weatherReport;
    }

    public void setWeatherReport(WeatherReport weatherReport) {
        this.weatherReport = weatherReport;
    }

    public Standings getStandings() {
        return standings;
    }

    public void setStandings(Standings standings) {
        this.standings = standings;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }

    public Corners getCorners() {
        return corners;
    }

    public void setCorners(Corners corners) {
        this.corners = corners;
    }

    public Substitutions getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(Substitutions substitutions) {
        this.substitutions = substitutions;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Integer getLocalteamId() {
        return localteam_id;
    }

    public void setLocalteamId(Integer localteam_id) {
        this.localteam_id = localteam_id;
    }

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public FlatOdds getFlatOdds() {
        return flatOdds;
    }

    public void setFlatOdds(FlatOdds flatOdds) {
        this.flatOdds = flatOdds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public Formations getFormations() {
        return formations;
    }

    public void setFormations(Formations formations) {
        this.formations = formations;
    }

    public Integer getVisitorteam_id() {
        return visitorteam_id;
    }

    public void setVisitorteam_id(Integer visitorteam_id) {
        this.visitorteam_id = visitorteam_id;
    }


    //  @JsonProperty("goals")
  //  private Goals goals;
/*    @JsonProperty("assistants")
    private Assistants assistants;
    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("bench")
    private Bench bench;
    @JsonProperty("visitorTeam")
    private VisitorTeam visitorTeam;
    @JsonProperty("stage_id")
    private Integer stageId;
    @JsonProperty("tvstations")
    private Tvstations tvstations;
    @JsonProperty("league")
    private League league;
    @JsonProperty("season_id")
    private Integer seasonId;
    @JsonProperty("coaches")
    private Coaches coaches;
    @JsonProperty("aggregate_id")
    private String aggregateId;
    @JsonProperty("winning_odds_calculated")
    private Boolean winningOddsCalculated;
    @JsonProperty("deleted")
    private Boolean deleted;
    @JsonProperty("highlights")
    private Highlights highlights;*/
  /*  @JsonProperty("round")
    private Round round;
    @JsonProperty("stage")
    private Stage stage;
    @JsonProperty("group_id")
    private String groupId;
    @JsonProperty("time")
    private Time time;
    @JsonProperty("sidelined")
    private Sidelined sidelined;
    @JsonProperty("league_id")
    private Integer leagueId;
    @JsonProperty("attendance")
    private String attendance;*/




  

    









}