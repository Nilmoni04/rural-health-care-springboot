// ===== CONFIG =====
// Use a relative path so the base URL is always the current origin — never user-controlled (prevents SSRF)
const API_BASE = '/api';

// ===== NAVIGATION =====
function showSection(name) {
  document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
  document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
  const target = document.getElementById(name);
  if (target) target.classList.add('active');
  const link = document.querySelector(`.nav-link[data-section="${name}"]`);
  if (link) link.classList.add('active');
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

document.querySelectorAll('.nav-link').forEach(link => {
  link.addEventListener('click', e => {
    e.preventDefault();
    showSection(link.dataset.section);
  });
});

// Hamburger menu
document.getElementById('hamburger').addEventListener('click', () => {
  document.querySelector('.nav-links').classList.toggle('open');
});

// ===== TOAST =====
function showToast(msg, type = 'success') {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = `toast ${type}`;
  setTimeout(() => t.classList.add('hidden'), 3500);
}

// ===== RESULT BOX =====
function showResult(id, data, isError = false) {
  const box = document.getElementById(id);
  box.classList.remove('hidden', 'error');
  if (isError) box.classList.add('error');
  if (typeof data === 'object') {
    box.innerHTML = formatObject(data);
  } else {
    box.textContent = data;
  }
}

function formatObject(obj) {
  return Object.entries(obj)
    .filter(([, v]) => v !== null && v !== undefined)
    .map(([k, v]) => {
      const label = k.replace(/([A-Z])/g, ' $1').replace(/^./, s => s.toUpperCase());
      return `<strong>${label}:</strong> ${typeof v === 'object' ? JSON.stringify(v) : v}`;
    })
    .join('<br>');
}

// ===== API HELPER =====
async function callApi(path, method = 'GET', body = null) {
  const opts = {
    method,
    headers: { 'Content-Type': 'application/json' }
  };
  if (body) opts.body = JSON.stringify(body);
  const res = await fetch(`${API_BASE}${path}`, opts);
  const data = await res.json();
  if (!res.ok) throw new Error(data.message || 'Request failed');
  return data;
}

// ===== APPOINTMENT FORM =====
document.getElementById('appointmentForm').addEventListener('submit', async e => {
  e.preventDefault();
  const btn = e.target.querySelector('button[type=submit]');
  btn.textContent = 'Booking...'; btn.disabled = true;
  try {
    const result = await callApi('/appointments', 'POST', {
      patientId: parseInt(document.getElementById('apt-patientId').value),
      doctorId: parseInt(document.getElementById('apt-doctorId').value),
      appointmentAt: document.getElementById('apt-date').value,
      reason: document.getElementById('apt-reason').value
    });
    showResult('apt-result', result);
    showToast('✅ Appointment booked successfully!');
    e.target.reset();
  } catch (err) {
    showResult('apt-result', '❌ ' + err.message, true);
    showToast(err.message, 'error');
  }
  btn.textContent = 'Book Appointment'; btn.disabled = false;
});

// ===== AMBULANCE FORM =====
document.getElementById('ambulanceForm').addEventListener('submit', async e => {
  e.preventDefault();
  const btn = e.target.querySelector('button[type=submit]');
  btn.textContent = 'Requesting...'; btn.disabled = true;
  try {
    const latVal = document.getElementById('amb-lat').value;
    const lngVal = document.getElementById('amb-lng').value;
    const payload = {
      patientId: parseInt(document.getElementById('amb-patientId').value),
      pickupAddress: document.getElementById('amb-pickup').value,
      destinationHospital: document.getElementById('amb-hospital').value,
      contactNumber: document.getElementById('amb-contact').value,
    };
    if (latVal) payload.latitude = parseFloat(latVal);
    if (lngVal) payload.longitude = parseFloat(lngVal);

    const result = await callApi('/ambulances', 'POST', payload);
    showResult('amb-result', result);
    showToast('🚑 Ambulance request sent!');
    e.target.reset();
  } catch (err) {
    showResult('amb-result', '❌ ' + err.message, true);
    showToast(err.message, 'error');
  }
  btn.textContent = '🚨 Request Ambulance Now'; btn.disabled = false;
});

// ===== TELECONSULT FORM =====
document.getElementById('teleForm').addEventListener('submit', async e => {
  e.preventDefault();
  const btn = e.target.querySelector('button[type=submit]');
  btn.textContent = 'Scheduling...'; btn.disabled = true;
  try {
    const docId = document.getElementById('tele-doctorId').value;
    const payload = {
      patientId: parseInt(document.getElementById('tele-patientId').value),
      scheduledAt: document.getElementById('tele-date').value,
      notes: document.getElementById('tele-notes').value,
    };
    if (docId) payload.doctorId = parseInt(docId);
    const result = await callApi('/teleconsults', 'POST', payload);
    showResult('tele-result', result);
    showToast('💻 Teleconsultation scheduled!');
    e.target.reset();
  } catch (err) {
    showResult('tele-result', '❌ ' + err.message, true);
    showToast(err.message, 'error');
  }
  btn.textContent = 'Schedule Teleconsultation'; btn.disabled = false;
});

// ===== REGISTER FORM =====
document.getElementById('registerForm').addEventListener('submit', async e => {
  e.preventDefault();
  const btn = e.target.querySelector('button[type=submit]');
  btn.textContent = 'Registering...'; btn.disabled = true;
  try {
    const result = await callApi('/patients', 'POST', {
      name: document.getElementById('reg-name').value,
      email: document.getElementById('reg-email').value,
      age: parseInt(document.getElementById('reg-age').value),
      gender: document.getElementById('reg-gender').value,
      address: document.getElementById('reg-address').value,
      language: document.getElementById('reg-language').value,
    });
    showResult('reg-result', `✅ Patient registered! Your Patient ID is: <strong>${result.id}</strong><br>Please save this ID — you will need it to book appointments.`);
    showToast('🎉 Registration successful! ID: ' + result.id);
    e.target.reset();
  } catch (err) {
    showResult('reg-result', '❌ ' + err.message, true);
    showToast(err.message, 'error');
  }
  btn.textContent = 'Register Patient'; btn.disabled = false;
});

// ===== CHATBOT =====
const chatWindow = document.getElementById('chatWindow');
const chatMessages = document.getElementById('chatMessages');
const chatInput = document.getElementById('chatInput');
let chatHistory = [];

document.getElementById('chatToggle').addEventListener('click', () => {
  chatWindow.classList.toggle('open');
  if (chatWindow.classList.contains('open')) chatInput.focus();
});

document.getElementById('chatClose').addEventListener('click', () => {
  chatWindow.classList.remove('open');
});

chatInput.addEventListener('keydown', e => {
  if (e.key === 'Enter') sendMessage();
});

function sendSuggestion(text) {
  chatInput.value = text;
  sendMessage();
}

function appendMessage(role, text) {
  const div = document.createElement('div');
  div.className = `chat-msg ${role}`;
  const avatar = document.createElement('span');
  avatar.className = 'msg-avatar';
  avatar.textContent = role === 'bot' ? '🤖' : '👤';
  const bubble = document.createElement('div');
  bubble.className = 'msg-bubble';
  bubble.innerHTML = text.replace(/\n/g, '<br>');
  div.appendChild(avatar);
  div.appendChild(bubble);
  chatMessages.appendChild(div);
  chatMessages.scrollTop = chatMessages.scrollHeight;
  return div;
}

function removeTyping(el) {
  if (el && el.parentNode) el.parentNode.removeChild(el);
}

async function sendMessage() {
  const text = chatInput.value.trim();
  if (!text) return;
  chatInput.value = '';

  appendMessage('user', text);
  chatHistory.push({ role: 'user', content: text });

  const typingEl = appendMessage('bot typing', '⏳ Thinking...');

  try {
    const response = await fetch(`${API_BASE}/chat`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ messages: chatHistory })
    });

    const data = await response.json();
    removeTyping(typingEl);

    const reply = data.reply || 'Sorry, I could not get a response. Please try again.';
    chatHistory.push({ role: 'assistant', content: reply });
    appendMessage('bot', reply);

    // Keep history at last 10 messages
    if (chatHistory.length > 10) chatHistory = chatHistory.slice(-10);

  } catch (err) {
    removeTyping(typingEl);
    appendMessage('bot', '⚠️ Sorry, I\'m having trouble connecting. Please try again in a moment.');
  }
}
