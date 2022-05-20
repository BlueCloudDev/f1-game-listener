INSERT INTO F12021.t_lap_data_2021 (
  m_index,
  m_packet_id,
  m_session_lookup_id,
  m_last_lap_time_in_ms,
  m_current_lap_time_in_ms,
  m_sector_1_time_in_ms,
  m_sector_2_time_in_ms,
  m_lap_distance,
  m_total_distance,
  m_safety_car_delta,
  m_car_position,
  m_current_lap_num,
  m_pit_status,
  m_num_pit_stops,
  m_sector,
  m_current_lap_invalid,
  m_penalties,
  m_warnings,
  m_num_unserved_drive_through_pens,
  m_num_unserved_stop_go_pens,
  m_grid_position,
  m_driver_status,
  m_result_status,
  m_pit_lane_timer_active,
  m_pit_lane_time_in_lane_in_ms,
  m_pit_stop_time_in_ms,
  m_pit_stop_should_serve_pen
) VALUES (
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?,?
)