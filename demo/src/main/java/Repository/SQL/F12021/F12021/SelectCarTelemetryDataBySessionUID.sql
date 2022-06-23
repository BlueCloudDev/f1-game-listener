SELECT 
  f12021.t_session_lookup_2021.m_frame_identifier,
  f12021.t_lap_data_2021.m_current_lap_num,
  f12021.t_lap_data_2021.m_lap_distance,
  f12021.t_car_telemetry_data_2021.m_speed,
  f12021.t_car_telemetry_data_2021.m_throttle,
  f12021.t_car_telemetry_data_2021.m_steer,
  f12021.t_car_telemetry_data_2021.m_brake,
  f12021.t_car_telemetry_data_2021.m_drs,
  f12021.t_car_telemetry_data_2021.m_engine_rpm
FROM f12021.t_session_lookup_2021
JOIN f12021.t_lap_data_2021 ON f12021.t_lap_data_2021.m_session_lookup_id = f12021.t_session_lookup_2021.id
JOIN f12021.t_car_telemetry_data_2021 ON f12021.t_car_telemetry_data_2021.m_session_lookup_id = f12021.t_session_lookup_2021.id
WHERE f12021.t_session_lookup_2021.m_session_uid = ?
AND f12021.t_session_lookup_2021.m_player_car_index = f12021.t_lap_data_2021.m_index
ORDER BY  f12021.t_session_lookup_2021.m_frame_identifier DESC