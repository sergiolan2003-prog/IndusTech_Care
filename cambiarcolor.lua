local TweenService = game:GetService("TweenService")

-- Obtener los modelos
local carpeta = workspace:WaitForChild("aura")
local vis2 = carpeta:WaitForChild("Vis2")
local vis3 = carpeta:WaitForChild("Vis3")
local rig = workspace:WaitForChild("nose")
local humanoidRootPart = rig:WaitForChild("HumanoidRootPart")

-- Colores brillantes para sincronizar
local colores = {
	Rojo = Color3.new(1, 0, 0),
	Naranja = Color3.new(1, 0.5, 0),
	Amarillo = Color3.new(1, 1, 0),
	Verde = Color3.new(0, 1, 0),
	Azul = Color3.new(0, 0, 1),
	Violeta = Color3.new(0.6, 0, 1),
	Magenta = Color3.new(1, 0, 1),
	Cian = Color3.new(0, 1, 1),
}

-- Función para cambiar el color de las partes de Vis2 y Vis3
local function changePartsColor(targetColor)
	-- Cambiar el color de las partes en Vis2
	for _, part in ipairs(vis2:GetChildren()) do
		if part:IsA("BasePart") then
			local tweenInfo = TweenInfo.new(2, Enum.EasingStyle.Linear, Enum.EasingDirection.InOut)
			local tween = TweenService:Create(part, tweenInfo, {Color = targetColor})
			tween:Play()
		end
	end
		-- Cambiar el color de las partes en Vis3
	for _, part in ipairs(vis3:GetChildren()) do
		if part:IsA("BasePart") then
			local tweenInfo = TweenInfo.new(2, Enum.EasingStyle.Linear, Enum.EasingDirection.InOut)
			local tween = TweenService:Create(part, tweenInfo, {Color = targetColor})
			tween:Play()
		end
	end
end

-- Función para cambiar el color de los ParticleEmitters en el HumanoidRootPart del rig
local function changeParticleEmittersColor(targetColor)
	-- Recorrer todos los attachments dentro del HumanoidRootPart
	for _, attachment in ipairs(humanoidRootPart:GetChildren()) do
		if attachment:IsA("Attachment") then
			-- Recorrer todos los ParticleEmitters dentro de cada Attachment
			for _, particleEmitter in ipairs(attachment:GetChildren()) do
				if particleEmitter:IsA("ParticleEmitter") then
					-- Cambiar el color del ParticleEmitter
					local particleColor = ColorSequence.new(targetColor)
					particleEmitter.Color = particleColor
				end
			end
		end
	end
end

-- Función para cambiar el color del ParticleEmitter de manera sincronizada
local function changeColorSynced(targetColor)
	-- Cambiar el color de las partes de Vis2 y Vis3
	changePartsColor(targetColor)

	-- Cambiar el color de los ParticleEmitters en nose
	changeParticleEmittersColor(targetColor)
end

-- Ciclo para cambiar de color cada 2 segundos
while true do
	for colorNombre, color in pairs(colores) do
		-- Cambiar el color sincronizado de las partes y los ParticleEmitters
		changeColorSynced(color)

		wait(2) -- Esperar 2 segundos antes de cambiar al siguiente color
	end
end
