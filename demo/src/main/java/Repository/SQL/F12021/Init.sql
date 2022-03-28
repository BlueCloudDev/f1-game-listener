DROP TABLE t_tyre_stint_history_data_2021;
DROP TABLE t_lap_history_data_2021;
DROP TABLE t_session_history_data_2021;
DROP TABLE t_car_damage_data_2021;
DROP TABLE t_final_classification_data_2021;
DROP TABLE t_weather_forecast_samples_2021;
DROP TABLE t_marshal_zone_2021;
DROP TABLE t_session_data_2021;
DROP TABLE t_car_motion_data_player_2021;
DROP TABLE t_car_motion_data_2021;
DROP TABLE t_car_telemetry_data_2021;
DROP TABLE t_car_setup_data_2021;
DROP TABLE t_car_status_data_2021;
DROP TABLE t_participant_data_2021;
DROP TABLE t_lap_data_2021;
DROP TABLE t_packet_header_2021;

CREATE TABLE t_packet_header_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_player_name VARCHAR2(100),
  m_packet_format NUMBER,
  m_game_major_version NUMBER,
  m_game_minor_version NUMBER,
  m_packet_version NUMBER,
  m_packet_id NUMBER,
  m_session_UID VARCHAR2(200) NOT NULL,
  m_session_time NUMBER,
  m_frame_identifier NUMBER,
  m_player_car_index NUMBER,
  m_secondary_player_car_index NUMBER,
  CONSTRAINT t_packet_header_2021_pk PRIMARY KEY (id),
  CONSTRAINT unique_packet_header_2021 UNIQUE (m_session_UID, m_packet_id, m_frame_identifier)
);

CREATE TABLE t_car_telemetry_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER,
  m_index NUMBER,
  m_speed NUMBER,
  m_throttle NUMBER,
  m_steer NUMBER,
  m_brake NUMBER,
  m_clutch NUMBER,
  m_gear NUMBER,
  m_engine_rpm NUMBER,
  m_drs NUMBER,
  m_rev_lights_percent NUMBER,
  m_rev_lights_bit_value NUMBER,
  m_brakes_temperature_RL NUMBER,
  m_brakes_temperature_RR NUMBER,
  m_brakes_temperature_FL NUMBER,
  m_brakes_temperature_FR NUMBER,
  m_tyres_surface_temperature_RL NUMBER,
  m_tyres_surface_temperature_RR NUMBER,
  m_tyres_surface_temperature_FL NUMBER,
  m_tyres_surface_temperature_FR NUMBER,
  m_tyres_inner_temperature_RL NUMBER,
  m_tyres_inner_temperature_RR NUMBER,
  m_tyres_inner_temperature_FL NUMBER,
  m_tyres_inner_temperature_FR NUMBER,
  m_engine_temperature NUMBER,
  m_tyres_pressure_RL NUMBER,
  m_tyres_pressure_RR NUMBER,
  m_tyres_pressure_FL NUMBER,
  m_tyres_pressure_FR NUMBER,
  m_surface_type_RL VARCHAR2(100),
  m_surface_type_RR VARCHAR2(100),
  m_surface_tyep_FL VARCHAR2(100),
  m_surface_type_FR VARCHAR2(100),
  CONSTRAINT t_car_telemetry_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_car_telemetry_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_car_status_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER,
  m_index NUMBER,
  m_traction_control NUMBER,
  m_anti_lock_brakes NUMBER,
  m_fuel_mix VARCHAR2(100),
  m_front_brake_bias NUMBER,
  m_pit_limiter_status NUMBER,
  m_fuel_in_tank NUMBER,
  m_fuel_capacity NUMBER,
  m_fuel_remaining_laps NUMBER,
  m_max_rpm NUMBER,
  m_idel_rpm NUMBER,
  m_max_gears NUMBER,
  m_drs_allowed VARCHAR2(100),
  m_drs_activation_distance NUMBER,
  m_actual_tyre_compound VARCHAR2(100),
  m_visual_tyre_compound VARCHAR2(100),
  m_tyres_age_laps NUMBER,
  m_vehicle_fia_flags VARCHAR2(100),
  m_ers_store_energy NUMBER,
  m_ers_deploy_mode VARCHAR2(100),
  m_ers_harvested_this_lap_MGUK NUMBER,
  m_ers_harvested_this_lap_MGUH NUMBER,
  m_ers_deployed_this_lap NUMBER,
  m_network_paused NUMBER,
  CONSTRAINT t_car_status_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_car_status_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_lap_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_index NUMBER,
  m_packet_id NUMBER,
  m_last_lap_time_in_ms NUMBER,
  m_current_lap_time_in_ms NUMBER,
  m_sector_1_time_in_ms NUMBER,
  m_sector_2_time_in_ms NUMBER,
  m_lap_distance NUMBER,
  m_total_distance NUMBER,
  m_safety_car_delta NUMBER,
  m_car_position NUMBER,
  m_current_lap_num NUMBER,
  m_pit_status VARCHAR2(100),
  m_num_pit_stops NUMBER,
  m_sector NUMBER,
  m_current_lap_invalid NUMBER,
  m_penalties NUMBER,
  m_warnings NUMBER,
  m_num_unserved_drive_through_pens NUMBER,
  m_num_unserved_stop_go_pens NUMBER,
  m_grid_position NUMBER,
  m_driver_status VARCHAR2(100),
  m_result_status VARCHAR2(100),
  m_pit_lane_timer_active VARCHAR2(100),
  m_pit_lane_time_in_lane_in_ms NUMBER,
  m_pit_stop_time_in_ms NUMBER,
  m_pit_stop_should_serve_pen NUMBER,
  CONSTRAINT t_lap_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_lap_data_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_participant_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_index NUMBER,
  m_packet_id NUMBER,
  m_ai_controlled NUMBER,
  m_driver_id VARCHAR2(100),
  m_network_id NUMBER,
  m_team_id VARCHAR2(100),
  m_race_number NUMBER,
  m_nationality VARCHAR(100),
  m_name VARCHAR2(100),
  m_num_active_cars NUMBER,
  CONSTRAINT t_participant_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_participant_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_car_setup_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER,
  m_index NUMBER,
  m_front_wing NUMBER,
  m_rear_wing NUMBER,
  m_on_throttle NUMBER,
  m_off_throttle NUMBER,
  m_front_camber NUMBER,
  m_rear_camber NUMBER,
  m_front_toe NUMBER,
  m_rear_toe NUMBER,
  m_front_suspension NUMBER,
  m_rear_suspension NUMBER,
  m_front_anti_roll_bar NUMBER,
  m_rear_anti_roll_bar NUMBER,
  m_front_suspension_height NUMBER,
  m_rear_suspension_height NUMBER,
  m_brake_pressure NUMBER,
  m_brake_bias NUMBER,
  m_rear_left_tyre_pressure NUMBER,
  m_rear_right_tyre_pressure NUMBER,
  m_front_left_tyre_pressure NUMBER,
  m_front_right_tyre_pressure NUMBER,
  m_ballast NUMBER,
  m_fuel_load NUMBER,
  CONSTRAINT t_car_setup_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_car_setup_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_car_motion_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER,
  m_index NUMBER,
  m_world_position_x NUMBER,
  m_world_position_y NUMBER,
  m_world_position_z NUMBER,
  m_world_velocity_x NUMBER,
  m_world_velocity_y NUMBER,
  m_world_velocity_z NUMBER,
  m_world_forward_dir_x NUMBER,
  m_world_forward_dir_y NUMBER,
  m_world_forward_dir_z NUMBER,
  m_world_right_dir_x NUMBER,
  m_world_right_dir_y NUMBER,
  m_world_right_dir_z NUMBER,
  m_g_force_lateral NUMBER,
  m_g_force_longitudinal NUMBER,
  m_yaw NUMBER,
  m_pitch NUMBER,
  m_roll NUMBER,
  CONSTRAINT t_car_motion_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_car_motion_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_car_motion_data_player_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_car_motion_data_id NUMBER,
  m_suspension_position_RL NUMBER,
  m_suspension_position_RR NUMBER,
  m_suspension_position_FL NUMBER,
  m_suspension_position_FR NUMBER,
  m_suspension_velocity_RL NUMBER,
  m_suspension_velocity_RR NUMBER,
  m_suspension_velocity_FL NUMBER,
  m_suspension_velocity_FR NUMBER,
  m_suspension_acceleration_RL NUMBER,
  m_suspension_acceleration_RR NUMBER,
  m_suspension_acceleration_FL NUMBER,
  m_suspension_acceleration_FR NUMBER,
  m_wheel_speed_RL NUMBER,
  m_wheel_speed_RR NUMBER,
  m_wheel_speed_FL NUMBER,
  m_wheel_speed_FR NUMBER,
  m_wheel_slip_RL NUMBER,
  m_wheel_slip_RR NUMBER,
  m_wheel_slip_FL NUMBER,
  m_wheel_slip_FR NUMBER,
  m_local_velocity_x NUMBER,
  m_local_velocity_Y NUMBER,
  m_local_velocity_z NUMBER,
  m_angular_velocity_X NUMBER,
  m_angular_velocity_y NUMBER,
  m_angular_velocity_z NUMBER,
  m_angular_acceleration_x NUMBER,
  m_angular_acceleration_y NUMBER,
  m_angular_acceleration_z NUMBER,
  m_front_wheels_angle NUMBER,
  CONSTRAINT t_car_motion_data_player_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_player_car_motion_data_2021_fk FOREIGN KEY (m_car_motion_data_id) REFERENCES t_car_motion_data (id)
); 

CREATE TABLE t_session_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER NOT NULL,
  m_weather VARCHAR2(100),
  m_track_temperature NUMBER,
  m_air_temperature NUMBER,
  m_total_laps NUMBER,
  m_track_length NUMBER,
  m_session_type VARCHAR2(100),
  m_track_id VARCHAR2(100),
  m_formula VARCHAR2(100),
  m_session_time_left NUMBER,
  m_session_duration NUMBER,
  m_pit_speed_limit NUMBER,
  m_game_paused NUMBER,
  m_is_spectating NUMBER,
  m_spectator_car_index NUMBER,
  m_sli_pro_native_support NUMBER,
  m_car_safety_status VARCHAR2(100),
  m_network_game VARCHAR2(100),
  m_forecast_accuracy VARCHAR2(100),
  m_ai_difficulty NUMBER,
  m_season_link_identifier NUMBER,
  m_weekend_link_identifier NUMBER,
  m_session_link_identifier NUMBER,
  m_pit_stop_window_ideal_lap NUMBER,
  m_pit_stop_window_latest_lap NUMBER,
  m_pit_stop_rejoin_position NUMBER,
  m_steering_assist NUMBER,
  m_braking_assist VARCHAR2(100),
  m_gear_box_assist VARCHAR2(100),
  m_pit_assist NUMBER,
  m_pit_release_assist NUMBER,
  m_ers_assist NUMBER,
  m_drs_assist NUMBER,
  m_dynamic_racing_line VARCHAR2(100),
  m_dynamic_racing_ling_type VARCHAR2(100),
  CONSTRAINT t_session_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_session_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_marshal_zone_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_data_id NUMBER,
  m_zone_start NUMBER,
  m_zone_flag VARCHAR2(100),
  CONSTRAINT t_marshal_zone_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_session_data_marshal_2021_fk FOREIGN KEY (m_session_data_id) REFERENCES t_session_data (id)
);


CREATE TABLE t_weather_forecast_samples_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_data_id NUMBER,
  m_session_type VARCHAR2(100),
  m_time_offset NUMBER,
  m_weather VARCHAR2(100),
  m_track_temperature NUMBER,
  m_track_temperature_change VARCHAR2(100),
  m_air_temperature NUMBER,
  m_air_temperature_change VARCHAR2(100),
  m_rain_percentage NUMBER,
  CONSTRAINT t_weather_forecast_samples_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_session_data_weather_2021_fk FOREIGN KEY (m_session_data_id) REFERENCES t_session_data (id)
);

CREATE TABLE t_final_classification_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER NOT NULL,
  m_position NUMBER,
  m_num_laps NUMBER,
  m_grid_position NUMBER,
  m_points NUMBER,
  m_num_pit_stops NUMBER,
  m_result_status VARCHAR2(100),
  m_best_lap_time_in_ms NUMBER,
  m_total_race_time NUMBER,
  m_penalties_time NUMBER,
  m_num_tyre_stints NUMBER,
  m_tyre_stints_actual_0 NUMBER,
  m_tyre_stints_actual_1 NUMBER,
  m_tyre_stints_actual_2 NUMBER,
  m_tyre_stints_actual_3 NUMBER,
  m_tyre_stints_actual_4 NUMBER,
  m_tyre_stints_actual_5 NUMBER,
  m_tyre_stints_actual_6 NUMBER,
  m_tyre_stints_actual_7 NUMBER,
  m_tyre_stints_visual_0 NUMBER,
  m_tyre_stints_visual_1 NUMBER,
  m_tyre_stints_visual_2 NUMBER,
  m_tyre_stints_visual_3 NUMBER,
  m_tyre_stints_visual_4 NUMBER,
  m_tyre_stints_visual_5 NUMBER,
  m_tyre_stints_visual_6 NUMBER,
  m_tyre_stints_visual_7 NUMBER,
  CONSTRAINT t_final_classification_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_final_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_car_damage_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER NOT NULL,
  m_index NUMBER,
  m_tyres_wear_RL NUMBER,
  m_tyres_wear_RR NUMBER,
  m_tyres_wear_FL NUMBER,
  m_tyres_wear_FR NUMBER,
  m_tyres_damage_RL NUMBER,
  m_tyres_damage_RR NUMBER,
  m_tyres_damage_FL NUMBER,
  m_tyres_damage_FR NUMBER,
  m_brakes_damage_RL NUMBER,
  m_brakes_damage_RR NUMBER,
  m_brakes_damage_FL NUMBER,
  m_brakes_damage_FR NUMBER,
  m_front_left_wing_damage NUMBER,
  m_front_right_wing_damage NUMBER,
  m_rear_wing_damage NUMBER,
  m_floor_damage NUMBER,
  m_diffuser_damage NUMBER,
  m_sidepod_damage NUMBER,
  m_drs_fault VARCHAR2(100),
  m_gear_box_damage NUMBER,
  m_engine_damage NUMBER,
  m_engine_MGUH_wear NUMBER,
  m_engine_ES_wear NUMBER,
  m_engine_CE_wear NUMBER,
  m_engine_ICE_wear NUMBER,
  m_engine_MGUK_wear NUMBER,
  m_engine_TC_wear NUMBER,
  CONSTRAINT t_car_damage_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_damage_packet_2021_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header_2021 (id)
);

CREATE TABLE t_session_history_data_2021 (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_UID VARCHAR2(200) NOT NULL,
  m_index NUMBER NOT NULL,
  m_best_lap_time_lap_num NUMBER,
  m_best_sector_1_lap_num NUMBER,
  m_best_sector_2_lap_num NUMBER,
  m_best_sector_3_lap_num NUMBER,
  CONSTRAINT t_session_history_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT unique_session_history_2021 UNIQUE (m_session_UID, m_index)
);

CREATE TABLE t_lap_history_data_2021(
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_history_id NUMBER NOT NULL,
  m_lap_number NUMBER,
  m_lap_time_in_ms NUMBER,
  m_sector_1_time_in_ms NUMBER,
  m_sector_2_time_in_ms NUMBER,
  m_sector_3_time_in_ms NUMBER,
  m_lap_valid_bit_flags NUMBER,
  CONSTRAINT t_lap_history_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_lap_history_2021_fk FOREIGN KEY (m_session_history_id) REFERENCES t_session_history_data_2021 (id)
);

CREATE TABLE t_tyre_stint_history_data_2021(
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_history_id NUMBER NOT NULL,
  m_tyre_actual_compound VARCHAR2(100),
  m_tyre_visual_compound VARCHAR2(100),
  m_stint_number NUMBER
  CONSTRAINT t_tyre_stint_history_data_2021_pk PRIMARY KEY (id),
  CONSTRAINT t_tyre_stint_history_2021_fk FOREIGN KEY (m_session_history_id) REFERENCES t_session_history_data_2021 (id)
);