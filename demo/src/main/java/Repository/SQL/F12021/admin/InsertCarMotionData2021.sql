INSERT INTO t_car_motion_data_2021 (
  m_packet_id,
  m_index,
  m_session_lookup_id,
  m_world_position_x,
  m_world_position_y,
  m_world_position_z,
  m_world_velocity_x,
  m_world_velocity_y,
  m_world_velocity_z,
  m_world_forward_dir_x,
  m_world_forward_dir_y,
  m_world_forward_dir_z,
  m_world_right_dir_x,
  m_world_right_dir_y,
  m_world_right_dir_z,
  m_g_force_lateral,
  m_g_force_longitudinal,
  m_yaw,
  m_pitch,
  m_roll
) VALUES (
  ?,?,?,?,?,?,?,?,?,?,
  ?,?,?,?,?,?,?,?,?,?
)