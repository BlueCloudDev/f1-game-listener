DROP TABLE t_weather_forecast_samples;
DROP TABLE t_marshal_zone;
DROP TABLE t_session_data;
DROP TABLE t_car_motion_data_player;
DROP TABLE t_car_motion_data;
DROP TABLE t_packet_header;

CREATE TABLE t_packet_header (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_format NUMBER,
  m_game_major_version NUMBER,
  m_game_minor_version NUMBER,
  m_packet_version NUMBER,
  m_packet_id NUMBER,
  m_session_UID NUMBER,
  m_session_time NUMBER,
  m_frame_identifier NUMBER,
  m_player_car_index NUMBER,
  m_secondary_player_car_index NUMBER,
  CONSTRAINT t_packet_header PRIMARY KEY (id),
  CONSTRAINT unique_packet_header UNIQUE (m_session_UID, m_packet_id, m_frame_identifier)
);

CREATE TABLE t_car_motion_data (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_packet_id NUMBER,
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
  CONSTRAINT t_car_motion_data PRIMARY KEY (id),
  CONSTRAINT t_car_motion_packet_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header (id)
);

CREATE TABLE t_car_motion_data_player (
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
  CONSTRAINT t_car_motion_data_player PRIMARY KEY (id),
  CONSTRAINT t_player_car_motion_data_fk FOREIGN KEY (m_car_motion_data_id) REFERENCES t_car_motion_data (id)
); 

CREATE TABLE t_session_data (
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
  m_car_safety_status VARCHAR(100),
  m_network_game VARCHAR(100),
  CONSTRAINT t_session_data PRIMARY KEY (id),
  CONSTRAINT t_session_packet_fk FOREIGN KEY (m_packet_id) REFERENCES t_packet_header (id)
);

CREATE TABLE t_marshal_zone (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_data_id NUMBER,
  m_zone_start NUMBER,
  m_zone_flag VARCHAR(100),
  CONSTRAINT t_marshal_zone_pk PRIMARY KEY (id),
  CONSTRAINT t_session_data_marshal_fk FOREIGN KEY (m_session_data_id) REFERENCES t_session_data (id)
);


CREATE TABLE t_weather_forecast_samples (
  id NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY,
  m_session_data_id NUMBER,
  m_session_type VARCHAR(100),
  m_time_offset NUMBER,
  m_weather VARCHAR(100),
  m_track_temperature NUMBER,
  m_air_temperature NUMBER,
  CONSTRAINT t_weather_forecast_samples_pk PRIMARY KEY (id),
  CONSTRAINT t_session_data_weather_fk FOREIGN KEY (m_session_data_id) REFERENCES t_session_data (id)
);