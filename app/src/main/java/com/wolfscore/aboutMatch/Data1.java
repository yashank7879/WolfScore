package com.wolfscore.aboutMatch;

/**
 * Created by JacksonGenerator on 13/2/19.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data1 {
    public Venue venue;
    public Cards cards;
    public Scores scores;
    public Referee referee;
    public Integer visitorteamId;
    public WeatherReport weatherReport;
    public Standings standings;
    public Colors colors;
    public Corners corners;
    public Substitutions substitutions;
    public Stats stats;
    public Odds odds;
    public FlatOdds flatOdds;

    public Events events;
    public League league;
    public Time time;
    public int attendance;


    public Boolean commentaries;
    public LocalTeam localTeam;
    public Lineup lineup;
    public Integer visitorteam_id;
    public String leg;
    public Integer roundId;
    public Integer refereeId;
    public Integer localteam_id;
    public Season season;
    public Integer id;
    public String pitch;
    public Integer venueId;
    public Formations formations;
    @JsonProperty("visitorTeam")
    public VisitorTeam visitorTeam;
    @JsonProperty("sidelined")
    public Sidelined sidelined;

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
    //  public VisitorTeam visitorTeam;


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
  //  public Goals goals;
/*    @JsonProperty("assistants")
    public Assistants assistants;
    @JsonProperty("comments")
    public Comments comments;
    @JsonProperty("bench")
    public Bench bench;
    @JsonProperty("visitorTeam")
    public VisitorTeam visitorTeam;
    @JsonProperty("stage_id")
    public Integer stageId;
    @JsonProperty("tvstations")
    public Tvstations tvstations;
    @JsonProperty("league")
    public League league;
    @JsonProperty("season_id")
    public Integer seasonId;
    @JsonProperty("coaches")
    public Coaches coaches;
    @JsonProperty("aggregate_id")
    public String aggregateId;
    @JsonProperty("winning_odds_calculated")
    public Boolean winningOddsCalculated;
    @JsonProperty("deleted")
    public Boolean deleted;
    @JsonProperty("highlights")
    public Highlights highlights;*/
  /*  @JsonProperty("round")
    public Round round;
    @JsonProperty("stage")
    public Stage stage;
    @JsonProperty("group_id")
    public String groupId;
    @JsonProperty("time")
    public Time time;
    @JsonProperty("sidelined")
    public Sidelined sidelined;
    @JsonProperty("league_id")
    public Integer leagueId;
    @JsonProperty("attendance")
    public String attendance;*/




  

    









}